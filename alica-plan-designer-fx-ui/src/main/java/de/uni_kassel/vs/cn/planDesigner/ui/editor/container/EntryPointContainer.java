package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.ChangePosition;
import de.uni_kassel.vs.cn.planDesigner.alica.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanEditorGroup;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

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
        setLayoutX(getPmlUiExtension().getXPos());
        setLayoutY(getPmlUiExtension().getYPos());
        visualRepresentation = new Circle(StateContainer.STATE_RADIUS,
                getVisualisationColor());
        setEffectToStandard();

        if (stateContainer != null) {
            double localX = stateContainer.getVisualRepresentation().getLayoutX();
            double localY = stateContainer.getVisualRepresentation().getLayoutY();
            Point2D planXY = stateContainer.localToParent(localX, localY);
            Point2D localXY = parentToLocal(planXY);

            Point2D vec = new Point2D(localXY.getX() - visualRepresentation.getLayoutX(), localXY.getY() - visualRepresentation.getLayoutY());
            double len = vec.magnitude() - StateContainer.STATE_RADIUS;
            vec = vec.normalize().multiply(len);

            Line line = new Line(visualRepresentation.getLayoutX(),
                    visualRepresentation.getLayoutY(),
                    vec.getX(),
                    vec.getY());
            line.getStrokeDashArray().addAll(2d, 10d);
            getChildren().add(line);
        }

        getChildren().add(visualRepresentation);
        Text e = new Text(getContainedElement().getTask().getName());
        getChildren().add(e);
        e.setLayoutX(e.getLayoutX() - e.getLayoutBounds().getWidth()/2);
        e.setLayoutY(e.getLayoutY() - StateContainer.STATE_RADIUS);
    }

    @Override
    public void setEffectToStandard() {
        visualRepresentation.setEffect(new DropShadow(BlurType.THREE_PASS_BOX,
                new Color(0,0,0,0.8), 10, 0, 0, 0));
    }

    @Override
    public Color getVisualisationColor() {
        return Color.BLUE;
    }

    @Override
    protected EventHandler<MouseEvent> getMouseClickedEventHandler(EntryPoint containedElement) {
        List<Pair<PlanElement, AbstractPlanElementContainer>> selected = new ArrayList<>();
        selected.add(new Pair<>(containedElement, this));
        return event -> ((PlanEditorGroup) getParent()).getPlanEditorTab().getSelectedPlanElement().setValue(selected);
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
                (int) (getLayoutY()), getContainedElement().getPlan());
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
