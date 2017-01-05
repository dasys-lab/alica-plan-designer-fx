package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.Command;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * This interface defines functions for draggable editor elements.
 */
public interface DraggableEditorElement {

    CommandStack getCommandStackForDrag();

    void redrawElement();

    Command createMoveElementCommand();

    default Node createWrapper(Node node) {
        return new Group(node);
    }


    default Node makeDraggable(Node node) {
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
    }

    final class DragContext {
        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }
}
