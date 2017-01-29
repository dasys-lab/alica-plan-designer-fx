package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.Command;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.ChangePosition;
import de.uni_kassel.vs.cn.planDesigner.alica.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.EditorConstants;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanEditorPane;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 *
 */
public class EntryPointContainer extends PlanElementContainer<EntryPoint> implements DraggableEditorElement {

    private PmlUiExtension pmlUiExtensionOfReferencedState;
    private boolean dragged;

    public EntryPointContainer(EntryPoint containedElement, PmlUiExtension pmlUiExtension,
                               PmlUiExtension pmlUiExtensionOfReferencedState, CommandStack commandStack) {
        super(containedElement, pmlUiExtension, commandStack);
        this.pmlUiExtensionOfReferencedState = pmlUiExtensionOfReferencedState;
        draw();
    }

    @Override
    public void draw() {
        getChildren().clear();
        visualRepresentation = new Circle(EditorConstants.PLAN_SHIFTING_PARAMETER, getPmlUiExtension().getYPos() + EditorConstants.PLAN_SHIFTING_PARAMETER, 20, Color.BLUE);
        Line line = new Line(EditorConstants.PLAN_SHIFTING_PARAMETER,
                getPmlUiExtension().getYPos() + EditorConstants.PLAN_SHIFTING_PARAMETER,
                pmlUiExtensionOfReferencedState.getXPos() + EditorConstants.PLAN_SHIFTING_PARAMETER + EditorConstants.SECTION_MARGIN,
                pmlUiExtensionOfReferencedState.getYPos() + EditorConstants.PLAN_SHIFTING_PARAMETER + EditorConstants.SECTION_MARGIN);
        line.getStrokeDashArray().addAll(2d, 10d);
        getChildren().add(line);
        getChildren().add(visualRepresentation);
        getChildren().add(new Text(EditorConstants.PLAN_SHIFTING_PARAMETER, getPmlUiExtension().getYPos() + EditorConstants.PLAN_SHIFTING_PARAMETER,
                getContainedElement().getTask().getName()));
        makeDraggable(this);
    }



    @Override
    public Node createWrapper(Node node) {
        return this;
    }

    @Override
    public CommandStack getCommandStackForDrag() {
        return commandStack;
    }

    @Override
    public void redrawElement() {
        ((PlanEditorPane) getParent()).visualize();
    }

    @Override
    public Command createMoveElementCommand() {
        return new ChangePosition(getPmlUiExtension(), getContainedElement(),
                (int) (getLayoutX() + getTranslateX() - EditorConstants.PLAN_SHIFTING_PARAMETER - EditorConstants.SECTION_MARGIN),
                (int) (getLayoutY() + getTranslateY() - EditorConstants.PLAN_SHIFTING_PARAMETER - EditorConstants.SECTION_MARGIN));
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
                    dragContext.initialTranslateX = node.getTranslateX();
                    dragContext.initialTranslateY = node.getTranslateY();
                });

        wrapGroup.addEventFilter(
                MouseEvent.MOUSE_DRAGGED,
                mouseEvent -> {
                    // shift node from its initial position by delta
                    // calculated from mouse cursor movement
                    node.setTranslateX(dragContext.initialTranslateX + mouseEvent.getX() - dragContext.mouseAnchorX);
                    node.setTranslateY(dragContext.initialTranslateY + mouseEvent.getY() - dragContext.mouseAnchorY);
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
