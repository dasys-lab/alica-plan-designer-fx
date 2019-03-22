package de.unikassel.vs.alica.planDesigner.view.editor.container;

import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.scene.Node;

/**
 * This interface defines functions for draggable editor elements.
 */
public interface DraggableEditorElement {
    void redrawElement();
    void setDragged(boolean dragged);
    boolean wasDragged();
    void makeDraggable(Node node);

    ViewModelElement getPlanElementViewModel();

    final class DragContext {
        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialLayoutX;
        public double initialLayoutY;
    }
}
