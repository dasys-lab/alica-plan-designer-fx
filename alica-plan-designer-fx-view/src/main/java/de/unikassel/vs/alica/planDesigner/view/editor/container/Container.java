package de.unikassel.vs.alica.planDesigner.view.editor.container;

import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanEditorGroup;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.AbstractTool;
import de.unikassel.vs.alica.planDesigner.view.menu.ShowGeneratedSourcesMenuItem;
import de.unikassel.vs.alica.planDesigner.view.model.*;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * The {@link Container} is a base class for visual representations, with a alicamodel object to hold changes from the visualisation
 * that will be written back to resource later.
 */
public abstract class Container extends Pane implements DraggableEditorElement {


    protected static final Effect standardEffect = new DropShadow(BlurType.THREE_PASS_BOX,
            new Color(0,0,0,0.8), 10, 0, 0, 0);

    protected PlanElementViewModel planElementViewModel;
    protected Node visualRepresentation;
    protected PlanTab planTab;

    /**
     * @param planElementViewModel
     * @param planTab
     */
    public Container(PlanElementViewModel planElementViewModel, PlanTab planTab) {
        this.planElementViewModel = planElementViewModel;
        this.planTab = planTab;
        setBackground(Background.EMPTY);
        setPickOnBounds(false);
        addEventFilter(MouseEvent.MOUSE_CLICKED, getMouseClickedEventHandler());
        setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent e) {
                if(planElementViewModel instanceof StateViewModel || planElementViewModel instanceof PlanTypeViewModel
                        || planElementViewModel instanceof SynchronisationViewModel) {
                    return;
                }

                ContextMenu contextMenu;
                if(planElementViewModel instanceof BehaviourViewModel || planElementViewModel instanceof PlanViewModel){
                     contextMenu = new ContextMenu(new ShowGeneratedSourcesMenuItem(planElementViewModel.getId()));
                } else {
                     contextMenu = new ContextMenu(new ShowGeneratedSourcesMenuItem(planElementViewModel.getParentId()));
                }
                contextMenu.show(Container.this, e.getScreenX(), e.getScreenY());
            }
        });
        // prohibit containers from growing indefinitely (especially transition containers)
        setMaxSize(1, 1);
    }

    public PlanEditorGroup getPlanEditorGroup() {
        return planTab.getPlanEditorGroup();
    }

    /**
     * Sets the selection flag for the editor when modelElementId is clicked.
     * Unless the last click was performed as part of a tool phase.
     *
     * @return
     */
    private EventHandler<MouseEvent> getMouseClickedEventHandler() {
        return event -> {
            // Was the last click performed in the context of a tool?
            AbstractTool recentlyDoneTool = Container.this.planTab.getEditorToolBar().getRecentlyDoneTool();
            if (recentlyDoneTool != null) {
                recentlyDoneTool.setRecentlyDone(false);
                event.consume();
            } else {
                // Find the first Container in the hierarchy above the targeted Node
                Node targetNode = event.getPickResult().getIntersectedNode();
                while(targetNode != null && !(targetNode instanceof Container)) {
                    targetNode = targetNode.getParent();
                }
                // If the targeted Container is this, select this and consume the event
                if(targetNode == this) {
                    handleMouseClickedEvent(event);
                }
                // If the targeted Container is not this (meaning it's a child of this) don't consume the event to
                // allow the targeted Container to be selected
            }
        };
    }

    protected void handleMouseClickedEvent(MouseEvent event) {
        Container.this.planTab.setSelectedContainer(Container.this);
        event.consume();
    }

    public Node getVisualRepresentation() {
        return visualRepresentation;
    }

    @Override
    public PlanElementViewModel getPlanElementViewModel() {
        return planElementViewModel;
    }

    public abstract void setupContainer();

    @Override
    public void makeDraggable(Node node) {
        final DragContext dragContext = new DragContext();

        // disable mouse events for all children
        node.addEventHandler(MouseEvent.ANY, Event::consume);

        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Container.this.setDragged(false);
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
                Container.this.setDragged(true);

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

                planTab.fireChangePositionEvent(this, planElementViewModel.getType(), node.getLayoutX(), node.getLayoutY());
                mouseEvent.consume();
                redrawElement();
            }
        });
    }

    /**
     * Making the {@link Container} update its position, whenever the {@link PlanElementViewModel}
     * changes its coordinates.
     *
     * Method also sets the current position according to the {@link PlanElementViewModel} on call.
     *
     * @param node  the Node to change the position of
     * @param planElementViewModel  the element, that containsPlan the coordinates to listen to
     */
    public void createPositionListeners(Node node, PlanElementViewModel planElementViewModel){
        //Set to initial Position
        node.setLayoutX(planElementViewModel.getXPosition());
        node.setLayoutY(planElementViewModel.getYPosition());

        //Create Listeners
        planElementViewModel.xPositionProperty().addListener((observable, oldValue, newValue) -> {
            node.setLayoutX(newValue.doubleValue());
            Platform.runLater(this::redrawElement);
        });

        planElementViewModel.yPositionProperty().addListener((observable, oldValue, newValue) -> {
            node.setLayoutY(newValue.doubleValue());
            Platform.runLater(this::redrawElement);
        });
    }

    public void createAbstractPlanToStateListeners(StateViewModel state) {
        state.getAbstractPlans().addListener(new ListChangeListener<PlanElementViewModel>() {
            @Override
            public void onChanged(Change<? extends PlanElementViewModel> c) {
                Platform.runLater(Container.this::redrawElement);
            }
        });
    }

    public void createTaskToEntryPointListeners(Node node, EntryPointViewModel entryPoint){

        entryPoint.taskProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(this::redrawElement);
        });
    }

    public void createNameListener() {
        planElementViewModel.nameProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(this::redrawElement);
        });
    }

    /**
     * Sets the standard effect for the {@link Container}.
     * This can be overwritten by a child class for individual styling.
     */
    public void setEffectToStandard() {
        setEffect(Container.standardEffect);
    }

    /**
     * Sets the custom effect for the {@link Container}.
     * This can be overwritten by a child class for individual styling.
     */
    public void setCustomEffect(Effect effect) {
        this.setEffect(effect);
    }

    public abstract Color getVisualisationColor();

    @Override
    public void redrawElement() {}

    @Override
    public void setDragged(boolean dragged) {}

    @Override
    public boolean wasDragged() {
        return false;
    }
}
