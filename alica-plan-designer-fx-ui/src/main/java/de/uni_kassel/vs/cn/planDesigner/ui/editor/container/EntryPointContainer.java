package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.ChangePosition;
import de.uni_kassel.vs.cn.planDesigner.alica.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanEditorPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Pair;

/**
 *
 */
public class EntryPointContainer extends AbstractPlanElementContainer<EntryPoint> {

    private StateContainer stateContainer;
    private boolean dragged;

    /**
     * This constructor is for dummy containers. NEVER use in real UI
     */
    public EntryPointContainer() {
        super(null, null, null);

    }

    /**
     * @param containedElement
     * @param pmlUiExtension
     * @param stateContainer
     * @param commandStack
     */
    public EntryPointContainer(EntryPoint containedElement, PmlUiExtension pmlUiExtension,
                               StateContainer stateContainer, CommandStack commandStack) {
        super(containedElement, pmlUiExtension, commandStack);
        if (stateContainer != null) {
            setStateContainer(stateContainer);
        }
        makeDraggable(this);
        setupContainer();
    }

    @Override
    public void setupContainer() {
        getChildren().clear();
        visualRepresentation = new Circle(getPmlUiExtension().getXPos(), getPmlUiExtension().getYPos(), StateContainer.STATE_RADIUS,
                getVisualisationColor());
        visualRepresentation.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");

        if (stateContainer != null) {
            Line line = new Line(getPmlUiExtension().getXPos(),
                    getPmlUiExtension().getYPos(),
                    stateContainer.getLayoutX(),
                    stateContainer.getLayoutY());
            line.getStrokeDashArray().addAll(2d, 10d);
            getChildren().add(line);
        }

        getChildren().add(visualRepresentation);
        getChildren().add(new Text(getPmlUiExtension().getXPos()- StateContainer.STATE_RADIUS, getPmlUiExtension().getYPos() - StateContainer.STATE_RADIUS,
                getContainedElement().getTask().getName()));
    }

    @Override
    public Color getVisualisationColor() {
        return Color.BLUE;
    }

    @Override
    protected EventHandler<MouseEvent> getMouseClickedEventHandler(EntryPoint containedElement) {
        return event -> ((PlanEditorPane) getParent()).getPlanEditorTab().getSelectedPlanElement().setValue(new Pair<>(containedElement, this));
    }

    @Override
    public CommandStack getCommandStackForDrag() {
        return commandStack;
    }

    @Override
    public void redrawElement() {
        setupContainer();
    }

    @Override
    public AbstractCommand createMoveElementCommand() {
        return new ChangePosition(getPmlUiExtension(), getContainedElement(),
                (int) (getLayoutX()),
                (int) (getLayoutY()));
    }

    @Override
    public void setDragged(boolean dragged) {
        this.dragged = dragged;
    }

    @Override
    public boolean wasDragged() {
        return dragged;
    }

    public void setStateContainer(StateContainer stateContainer) {
        this.stateContainer = stateContainer;
        stateContainer.addListener(observable -> setupContainer());
    }
}
