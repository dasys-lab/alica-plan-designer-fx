package de.unikassel.vs.alica.planDesigner.view.editor.container;

import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
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

    ViewModelElement getViewModelElement();

    final class DragContext {
        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialLayoutX;
        public double initialLayoutY;
    }
}
