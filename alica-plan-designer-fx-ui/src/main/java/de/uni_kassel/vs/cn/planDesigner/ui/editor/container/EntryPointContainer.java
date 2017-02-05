package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.Command;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.ChangePosition;
import de.uni_kassel.vs.cn.planDesigner.alica.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.EditorConstants;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanEditorPane;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 *
 */
public class EntryPointContainer extends PlanElementContainer<EntryPoint> {

    private StateContainer stateContainer;
    private boolean dragged;

    public EntryPointContainer(EntryPoint containedElement, PmlUiExtension pmlUiExtension,
                               StateContainer stateContainer, CommandStack commandStack) {
        super(containedElement, pmlUiExtension, commandStack);
        this.stateContainer = stateContainer;
        stateContainer.addListener(observable -> setupContainer());
        makeDraggable(this);
        setupContainer();
    }

    @Override
    public void setupContainer() {
        getChildren().clear();
        visualRepresentation = new Circle(getPmlUiExtension().getXPos(), getPmlUiExtension().getYPos(), StateContainer.STATE_RADIUS, Color.BLUE);
        Line line = new Line(getPmlUiExtension().getXPos(),
                getPmlUiExtension().getYPos(),
                stateContainer.getLayoutX(),
                stateContainer.getLayoutY());
        line.getStrokeDashArray().addAll(2d, 10d);
        getChildren().add(line);
        getChildren().add(visualRepresentation);
        getChildren().add(new Text(getPmlUiExtension().getXPos()- StateContainer.STATE_RADIUS, getPmlUiExtension().getYPos() - StateContainer.STATE_RADIUS,
                getContainedElement().getTask().getName()));
    }

    @Override
    protected EventHandler<MouseEvent> getMouseClickedEventHandler(EntryPoint containedElement) {
        return event -> ((PlanEditorPane) getParent().getParent()).getPlanEditorTab().getSelectedPlanElement().setValue(containedElement);
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
    public Command createMoveElementCommand() {
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
    }/*

    @Override
    public Node makeDraggable(Node node) {
        final DragContext dragContext = new DragContext();
        final Node wrapGroup = createWrapper(node);

        wrapGroup.addEventFilter(
                MouseEvent.ANY,
                mouseEvent -> {
                    // disable mouse events for all children
                    mouseEvent.consume();
                });

        wrapGroup.addEventFilter(
                MouseEvent.MOUSE_PRESSED,
                mouseEvent -> {
                    // remember initial mouse cursor coordinates
                    // and node position
                    dragContext.mouseAnchorX = mouseEvent.getX();
                    dragContext.mouseAnchorY = mouseEvent.getY();
                    dragContext.initialLayoutX = node.getTranslateX();
                    dragContext.initialLayoutY = node.getTranslateY();
                });

        wrapGroup.addEventFilter(
                MouseEvent.MOUSE_DRAGGED,
                mouseEvent -> {
                    // shift node from its initial position by delta
                    // calculated from mouse cursor movement
                    node.setTranslateX(dragContext.initialLayoutX + mouseEvent.getX() - dragContext.mouseAnchorX);
                    node.setTranslateY(dragContext.initialLayoutY + mouseEvent.getY() - dragContext.mouseAnchorY);
                });

        wrapGroup.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            // save final position in actual bendpoint
            getCommandStackForDrag().storeAndExecute(createMoveElementCommand());
            event.consume();
            redrawElement();
        });

        return wrapGroup;
    }*/
}
