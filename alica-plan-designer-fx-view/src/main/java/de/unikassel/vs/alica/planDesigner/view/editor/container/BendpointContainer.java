package de.unikassel.vs.alica.planDesigner.view.editor.container;

import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.model.BendPointViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The {@link BendpointContainer} class holds a visual representation of a {}.
 * It also containsPlan an object of elementType {@link} to hold modifications to it.
 * This modifications are later written back to the actual Resource.
 */
public class BendpointContainer extends Rectangle implements DraggableEditorElement {
    public static final double WIDTH = 10.0;
    public static final double HEIGHT = 10.0;

    protected ViewModelElement containedElement;
    protected boolean dragged;
    protected ViewModelElement parent;
    private PlanTab planTab;

    public BendpointContainer(ViewModelElement containedElement, ViewModelElement parent, PlanTab planTab) {
        super(0, 0, WIDTH, HEIGHT);
        this.containedElement = containedElement;
        this.parent = parent;
        this.planTab = planTab;
        init();
    }

    protected void init() {
        this.setLayoutX(((BendPointViewModel)containedElement).getX() - (WIDTH/2.0));
        this.setLayoutY(((BendPointViewModel)containedElement).getY() - (HEIGHT/2.0));
        setFill(getVisualisationColor());
        makeDraggable(this);
    }

    protected Color getVisualisationColor() {
        return Color.BLACK;
    }

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

                // TODO send event to controller
                mouseEvent.consume();
                redrawElement();
            }
        });
    }

    @Override
    public ViewModelElement getViewModelElement() {
        return containedElement;
    }

    public ViewModelElement getContainedElement() {
        return containedElement;
    }

    @Override
    public void redrawElement() {
        ((TransitionContainer) this.getParent()).setupContainer();
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
