package de.unikassel.vs.alica.planDesigner.view.editor.container;

import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.model.BendPointViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.PlanElementViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The {@link BendpointContainer} class holds a visual representation of a {}.
 * It also containsPlan an object of elementType {@link} to hold modifications to it.
 * This modifications are later written back to the actual Resource.
 */
public class BendpointContainer extends Container implements DraggableEditorElement {
    public static final double WIDTH = 10.0;
    public static final double HEIGHT = 10.0;

    protected ViewModelElement containedElement;
    protected boolean dragged;
    protected ViewModelElement parent;
    private PlanTab planTab;
    private TransitionContainer transitionContainer;

    public BendpointContainer(PlanElementViewModel containedElement, ViewModelElement parent, PlanTab planTab, TransitionContainer transitionContainer) {
        super(containedElement, planTab);
        this.containedElement = containedElement;
        this.parent = parent;
        this.planTab = planTab;
        this.setTransitionContainer(transitionContainer);
        setupContainer();
    }

    public Color getVisualisationColor() {
        return Color.BLACK;
    }

    @Override
    public void makeDraggable(Node node) {
        final DragContext dragContext = new DragContext();

        // disable mouse events for all children
        node.addEventHandler(MouseEvent.ANY, Event::consume);

        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                BendpointContainer.this.setDragged(false);
                // remember initial mouse cursor coordinates
                // and node position
                dragContext.mouseAnchorX = mouseEvent.getSceneX();
                dragContext.mouseAnchorY = mouseEvent.getSceneY();
                dragContext.initialLayoutX = node.getLayoutX();
                dragContext.initialLayoutY = node.getLayoutY();
            }
        });

        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // shift node from its initial position by delta
                // calculated from mouse cursor movement
                BendpointContainer.this.setDragged(true);

                // set temporary translation
                node.setTranslateX(mouseEvent.getSceneX() - dragContext.mouseAnchorX);
                node.setTranslateY(mouseEvent.getSceneY() - dragContext.mouseAnchorY);
                //System.out.println("X: " + mouseEvent.getX() + " Y:" + mouseEvent.getY());
            }
        });

        node.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
            // save final position in actual bendpoint
            if (wasDragged()) {
                // reset translation and set layout to actual position
                node.setTranslateX(0);
                node.setTranslateY(0);
                node.setLayoutX(dragContext.initialLayoutX + mouseEvent.getSceneX() - dragContext.mouseAnchorX);
                node.setLayoutY(dragContext.initialLayoutY + mouseEvent.getSceneY() - dragContext.mouseAnchorY);


                planTab.fireBendPointChangePositionEvent(this, this.parent, containedElement.getType(), node.getLayoutX(), node.getLayoutY());

                //getCommandStackForDrag().storeAndExecute(createMoveElementCommand());
                mouseEvent.consume();
                redrawElement();
                setDragged(false);
            }
        });
    }
//
//    @Override
//    public PlanElementViewModel getPlanElementViewModel() {
//        return containedElement;
//    }

    @Override
    public void setupContainer() {
        getChildren().clear();
        visualRepresentation = new Rectangle(0, 0, WIDTH, HEIGHT);
        setEffectToStandard();
        getChildren().add(visualRepresentation);

        this.setLayoutX(((BendPointViewModel)containedElement).getX() - (WIDTH/2.0));
        this.setLayoutY(((BendPointViewModel)containedElement).getY() - (HEIGHT/2.0));
        makeDraggable(this);
    }

    public ViewModelElement getContainedElement() {
        return containedElement;
    }

    @Override
    public void redrawElement() {
        getTransitionContainer().redrawElement();
    }

    @Override
    public void setDragged(boolean dragged) {
        this.dragged = dragged;
    }

    @Override
    public boolean wasDragged() {
        return dragged;
    }

    public TransitionContainer getTransitionContainer() {
        return transitionContainer;
    }

    public void setTransitionContainer(TransitionContainer transitionContainer) {
        this.transitionContainer = transitionContainer;
    }
}
