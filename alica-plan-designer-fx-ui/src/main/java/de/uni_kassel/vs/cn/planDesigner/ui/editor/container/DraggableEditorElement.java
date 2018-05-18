package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.command.CommandStack;
import javafx.scene.Node;

/**
 * This interface defines functions for draggable editor elements.
 */
public interface DraggableEditorElement {

    CommandStack getCommandStackForDrag();

    void redrawElement();

    AbstractCommand createMoveElementCommand();

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
