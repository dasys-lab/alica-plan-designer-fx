package de.uni_kassel.vs.cn.planDesigner.view.editor.container;


import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.AbstractTool;
import de.uni_kassel.vs.cn.planDesigner.view.menu.IShowGeneratedSourcesEventHandler;
import de.uni_kassel.vs.cn.planDesigner.view.menu.ShowGeneratedSourcesMenuItem;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * The {@link AbstractPlanElementContainer} is a base class for visual representations, with a alicamodel object to hold changes from the visualisation
 * that will be written back to resource later.
 */
public abstract class AbstractPlanElementContainer extends Pane implements DraggableEditorElement {

    private long modelElementId;
    private IShowGeneratedSourcesEventHandler showGeneratedSourcesEventHandler;
    protected Node visualRepresentation;
    protected Node wrapper;

    /**
     * @param modelElementId
     */
    public AbstractPlanElementContainer(long modelElementId, IShowGeneratedSourcesEventHandler showGeneratedSourcesEventHandler) {
        this.modelElementId = modelElementId;
        this.showGeneratedSourcesEventHandler = showGeneratedSourcesEventHandler;
        setBackground(Background.EMPTY);
        setPickOnBounds(false);
        addEventFilter(MouseEvent.MOUSE_CLICKED, getMouseClickedEventHandler(modelElementId));
        wrapper = this;
        setOnContextMenuRequested(e -> {
            ContextMenu contextMenu = new ContextMenu(new ShowGeneratedSourcesMenuItem(this.modelElementId, this.showGeneratedSourcesEventHandler));
            contextMenu.show(AbstractPlanElementContainer.this, e.getScreenX(), e.getScreenY());
        });
        // prohibit containers from growing indefinitely (especially transition containers)
        setMaxSize(1, 1);
    }

    /**
     * Sets the selection flag for the editor when modelElementId is clicked.
     * Unless the last click was performed as part of a tool phase.
     *
     * @param modelElementId
     * @return
     */
    @SuppressWarnings("unchecked")
    protected EventHandler<MouseEvent> getMouseClickedEventHandler(long modelElementId) {
        return event -> {
//            PlanTab planTab = ((PlanTab) MainWindowController.getInstance().getEditorTabPane().getSelectionModel().getSelectedItem());
//            // Was the last click performed in the context of a tool?
//            AbstractTool recentlyDoneTool = planTab.getPldToolBar().getRecentlyDoneTool();
//            if (recentlyDoneTool != null) {
//                recentlyDoneTool.setRecentlyDone(false);
//            } else {
//                ArrayList<Pair<Long, AbstractPlanElementContainer>> selectedElements = new ArrayList<>();
//                selectedElements.add(new Pair<>(modelElementId, this));
//                planTab.getSelectedPlanElements().setValue(selectedElements);
//            }
        };
    }


    public Node getVisualRepresentation() {
        return visualRepresentation;
    }

    public long getModelElementId() {
        return modelElementId;
    }

    public Node getWrapper() {
        return wrapper;
    }

    public abstract void setupContainer();

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
                    dragContext.mouseAnchorX = mouseEvent.getSceneX();
                    dragContext.mouseAnchorY = mouseEvent.getSceneY();
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
                    node.setTranslateX(mouseEvent.getSceneX() - dragContext.mouseAnchorX);
                    node.setTranslateY(mouseEvent.getSceneY() - dragContext.mouseAnchorY);
                    //System.out.println("X: " + mouseEvent.getX() + " Y:" + mouseEvent.getY());
                });

        node.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
            // save final position in actual bendpoint
            if (wasDragged()) {
                // reset translation and set layout to actual position
                node.setTranslateX(0);
                node.setTranslateY(0);
                node.setLayoutX(dragContext.initialLayoutX + mouseEvent.getSceneX() - dragContext.mouseAnchorX);
                node.setLayoutY(dragContext.initialLayoutY + mouseEvent.getSceneY() - dragContext.mouseAnchorY);

                // TODO: fire move event, inorder to create move command in the Model Manager
                //getCommandStackForDrag().storeAndExecute(createMoveElementCommand());
                mouseEvent.consume();
                redrawElement();
            }
        });
    }

    /**
     * Sets the standard effect for the {@link AbstractPlanElementContainer}.
     * This should be overwritten by a child class for individual styling. By default no effect is set.
     */
    public void setEffectToStandard() {
        setEffect(null);
    }

    public abstract Color getVisualisationColor();

    @Override
    public void redrawElement() {

    }

    @Override
    public void setDragged(boolean dragged) {

    }

    @Override
    public boolean wasDragged() {
        return false;
    }
}
