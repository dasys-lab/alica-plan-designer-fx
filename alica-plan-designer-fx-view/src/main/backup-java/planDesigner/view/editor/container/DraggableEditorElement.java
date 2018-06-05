package de.uni_kassel.vs.cn.planDesigner.view.editor.container;

import javafx.scene.Node;

/**
 * This interface defines functions for draggable editor elements.
 */
public interface DraggableEditorElement {
    //CommandStack getCommandStackForDrag();
    //AbstractCommand createMoveElementCommand();
    void redrawElement();
    void setDragged(boolean dragged);
    boolean wasDragged();
    void makeDraggable(Node node);

    final class DragContext {
        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialLayoutX;
        public double initialLayoutY;
    }
}
