package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.ChangePositionForBendpoint;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.Bendpoint;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.EditorConstants;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *  The {@link BendpointContainer} class holds a visual representation of a {@link de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.Bendpoint}.
 *  It also contains an object of type {@link Bendpoint} to hold modifications to it.
 *  This modifications are later written back to the actual Resource.
 */
public class BendpointContainer extends Rectangle {
    private static final double SHIFTING_CONSTANT = EditorConstants.PLAN_SHIFTING_PARAMETER-2.5;
    private Bendpoint containedElement;
    private PmlUiExtension pmlUiExtension;
    private CommandStack commandStack;
    private Group wrapGroup;

    public BendpointContainer(Bendpoint containedElement, PmlUiExtension pmlUiExtension, CommandStack commandStack) {
        super(containedElement.getXPos() + SHIFTING_CONSTANT,
                containedElement.getYPos() + SHIFTING_CONSTANT, 10,10);
        this.containedElement = containedElement;
        this.pmlUiExtension = pmlUiExtension;
        this.commandStack = commandStack;
        setFill(Color.BLACK);
        this.wrapGroup = makeDraggable(this);
    }

    public Bendpoint getContainedElement() {
        return containedElement;
    }

    public PmlUiExtension getPmlUiExtension() {
        return pmlUiExtension;
    }

    public Group makeDraggable(Node node) {
        final DragContext dragContext = new DragContext();
        final Group wrapGroup = new Group(node);

        wrapGroup.addEventFilter(
                MouseEvent.ANY,
                new EventHandler<MouseEvent>() {
                    public void handle(final MouseEvent mouseEvent) {
                            // disable mouse events for all children
                            mouseEvent.consume();
                    }
                });

        wrapGroup.addEventFilter(
                MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    public void handle(final MouseEvent mouseEvent) {
                        // remember initial mouse cursor coordinates
                        // and node position
                        dragContext.mouseAnchorX = mouseEvent.getX();
                        dragContext.mouseAnchorY = mouseEvent.getY();
                        dragContext.initialTranslateX =
                                node.getTranslateX();
                        dragContext.initialTranslateY =
                                node.getTranslateY();
                    }
                });

        wrapGroup.addEventFilter(
                MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
                    public void handle(final MouseEvent mouseEvent) {
                        // shift node from its initial position by delta
                        // calculated from mouse cursor movement
                        node.setTranslateX(
                                dragContext.initialTranslateX
                                        + mouseEvent.getX()
                                        - dragContext.mouseAnchorX);
                        node.setTranslateY(
                                dragContext.initialTranslateY
                                        + mouseEvent.getY()
                                        - dragContext.mouseAnchorY);
                    }
                });

        wrapGroup.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // save final position in actual bendpoint
                commandStack.storeAndExecute(new ChangePositionForBendpoint(containedElement,
                        (int)(getX() + getTranslateX() - SHIFTING_CONSTANT), (int)(getY() + getTranslateY() - SHIFTING_CONSTANT)));
                event.consume();
                ((TransitionContainer) BendpointContainer.this.getParent().getParent()).draw();
            }
        });

        return wrapGroup;
    }

    public Group getWrapGroup() {
        return wrapGroup;
    }

    private static final class DragContext {
        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }
}
