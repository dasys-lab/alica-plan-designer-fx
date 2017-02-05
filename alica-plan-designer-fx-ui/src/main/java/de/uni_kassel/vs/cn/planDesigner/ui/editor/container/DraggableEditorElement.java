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
