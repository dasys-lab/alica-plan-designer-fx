package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.Command;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.ChangePositionForBendpoint;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.Bendpoint;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The {@link BendpointContainer} class holds a visual representation of a {@link de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.Bendpoint}.
 * It also contains an object of type {@link Bendpoint} to hold modifications to it.
 * This modifications are later written back to the actual Resource.
 */
public class BendpointContainer extends Rectangle implements DraggableEditorElement {
    private static final double SQUARE_SIZE = 5;
    private static final double SHIFTING_CONSTANT = (SQUARE_SIZE / 2);
    private Bendpoint containedElement;
    private PmlUiExtension pmlUiExtension;
    private CommandStack commandStack;
    private boolean dragged;

    public BendpointContainer(Bendpoint containedElement, PmlUiExtension pmlUiExtension, CommandStack commandStack) {
        super(0, 0, 10, 10);
        this.setLayoutX(containedElement.getXPos());
        this.setLayoutY(containedElement.getYPos());
        this.containedElement = containedElement;
        this.pmlUiExtension = pmlUiExtension;
        this.commandStack = commandStack;
        setFill(Color.BLACK);
        makeDraggable(this);
    }

//    @Override
//    public Node makeDraggable(Node node) {
//        final DragContext dragContext = new DragContext();
//
//        node.addEventHandler(
//                MouseEvent.ANY,
//                mouseEvent -> {
//                    // disable mouse events for all children
//                    mouseEvent.consume();
//                });
//
//        node.addEventHandler(
//                MouseEvent.MOUSE_PRESSED,
//                mouseEvent -> {
//                    setDragged(false);
//                    // remember initial mouse cursor coordinates
//                    // and node position
//                    dragContext.mouseAnchorX = mouseEvent.getX();
//                    dragContext.mouseAnchorY = mouseEvent.getY();
//                    dragContext.initialLayoutX = node.getLayoutX();
//                    dragContext.initialLayoutY = node.getLayoutY();
//                });
//
//        node.addEventHandler(
//                MouseEvent.MOUSE_DRAGGED,
//                mouseEvent -> {
//                    // shift node from its initial position by delta
//                    // calculated from mouse cursor movement
//                    setDragged(true);
//
//                    // set temporary translation
//                    node.setTranslateX(mouseEvent.getX() - dragContext.mouseAnchorX);
//                    node.setTranslateY(mouseEvent.getY() - dragContext.mouseAnchorY);
//                });
//
//        node.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
//            // save final position in actual bendpoint
//            if (wasDragged()) {
//                // reset translation and set layout to actual position
//                node.setTranslateX(0);
//                node.setTranslateY(0);
//                node.relocate(dragContext.initialLayoutX + mouseEvent.getX() - dragContext.mouseAnchorX,
//                        dragContext.initialLayoutY + mouseEvent.getY() - dragContext.mouseAnchorY);
//
//                getCommandStackForDrag().storeAndExecute(createMoveElementCommand());
//                mouseEvent.consume();
//                redrawElement();
//            }
//        });
//
//        return node;
//    }

    @Override
    public void makeDraggable(Node node) {
        final DragContext dragContext = new DragContext();

        node.addEventHandler(
                MouseEvent.ANY,
                mouseEvent -> {
                    // disable mouse events for all children
                    mouseEvent.consume();
                });

        node.addEventHandler(
                MouseEvent.MOUSE_PRESSED,
                mouseEvent -> {
                    setDragged(false);
                    // remember initial mouse cursor coordinates
                    // and node position
                    dragContext.mouseAnchorX = mouseEvent.getX();
                    dragContext.mouseAnchorY = mouseEvent.getY();
                    dragContext.initialLayoutX = node.getLayoutX();
                    dragContext.initialLayoutY = node.getLayoutY();
                });

        node.addEventHandler(
                MouseEvent.MOUSE_DRAGGED,
                mouseEvent -> {
                    // shift node from its initial position by delta
                    // calculated from mouse cursor movement
                    setDragged(true);

                    // set temporary translation
                    //node.setLayoutX(dragContext.initialLayoutX + mouseEvent.getX() - dragContext.mouseAnchorX);
                    //node.setLayoutY(dragContext.initialLayoutY + mouseEvent.getY() - dragContext.mouseAnchorY);
                    //System.out.println("X: " + mouseEvent.getX() + " Y:" + mouseEvent.getY());
                });

        node.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
            // save final position in actual bendpoint
            if (wasDragged()) {
                // reset translation and set layout to actual position
                node.setTranslateX(0);
                node.setTranslateY(0);
                node.setLayoutX(dragContext.initialLayoutX + mouseEvent.getX() - dragContext.mouseAnchorX);
                node.setLayoutY(dragContext.initialLayoutY + mouseEvent.getY() - dragContext.mouseAnchorY);

                System.out.println("AFTER DRAG X: " + (mouseEvent.getX() - dragContext.mouseAnchorX) + " Y:" +
                        (mouseEvent.getY() - dragContext.mouseAnchorY));
                System.out.println("LAYOUT X: " + node.getLayoutX() + " Y:" + node.getLayoutY());
                getCommandStackForDrag().storeAndExecute(createMoveElementCommand());
                mouseEvent.consume();
                redrawElement();
            }
        });
    }

    public Bendpoint getContainedElement() {
        return containedElement;
    }

    public PmlUiExtension getPmlUiExtension() {
        return pmlUiExtension;
    }

    @Override
    public CommandStack getCommandStackForDrag() {
        return commandStack;
    }

    @Override
    public void redrawElement() {
        ((TransitionContainer) this.getParent()).setupContainer();
    }

    @Override
    public Command createMoveElementCommand() {
        return new ChangePositionForBendpoint(containedElement, (int) (getLayoutX()),
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
}
