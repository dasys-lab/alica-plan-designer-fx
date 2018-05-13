package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.AbstractEditorTab;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.PlanTab;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.AbstractTool;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.PLDToolBar;
import de.uni_kassel.vs.cn.planDesigner.ui.menu.ShowGeneratedSourcesMenuItem;
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
 * The {@link AbstractPlanElementContainer} is a base class for visual representations, with a model object to hold changes from the visualisation
 * that will be written back to resource later.
 */
public abstract class AbstractPlanElementContainer<T extends PlanElement> extends Pane implements DraggableEditorElement {

    private T containedElement;
    private PmlUiExtension pmlUiExtension;
    protected Node visualRepresentation;
    protected CommandStack commandStack;
    protected Node wrapper;

    /**
     *
     * @param containedElement
     * @param pmlUiExtension
     * @param commandStack
     */
    public AbstractPlanElementContainer(T containedElement, PmlUiExtension pmlUiExtension, CommandStack commandStack) {
        this.containedElement = containedElement;
        this.pmlUiExtension = pmlUiExtension;
        this.commandStack = commandStack;
        setBackground(Background.EMPTY);
        setPickOnBounds(false);
        addEventFilter(MouseEvent.MOUSE_CLICKED, getMouseClickedEventHandler(containedElement));
        wrapper = this;
        setOnContextMenuRequested(e -> {
            ContextMenu contextMenu = new ContextMenu(new ShowGeneratedSourcesMenuItem<T>(containedElement));
            contextMenu.show(AbstractPlanElementContainer.this, e.getScreenX(), e.getScreenY());
        });
    }

    /**
     * Sets the selection flag for the editor when containedElement is clicked.
     * Unless the last click was performed as part of a tool phase.
     * @param containedElement
     * @return
     */
    @SuppressWarnings("unchecked")
    protected EventHandler<MouseEvent> getMouseClickedEventHandler(T containedElement) {
        return event -> {
            PLDToolBar pldToolBar = ((PlanTab) MainController.getInstance().getEditorTabPane().getSelectionModel()
                    .getSelectedItem()).getPldToolBar();
            // Was the last click performed in the context of a tool?
            if (pldToolBar.anyToolsRecentlyDone() == false) {
                ArrayList<Pair<PlanElement, AbstractPlanElementContainer>> selectedElements = new ArrayList<>();
                selectedElements.add(new Pair<>(containedElement, this));
                ((AbstractEditorTab<PlanElement>)MainController.getInstance().getEditorTabPane().getSelectionModel()
                        .getSelectedItem()).getSelectedPlanElement().setValue(selectedElements);

            } else {
                AbstractTool recentlyDoneTool = pldToolBar.getRecentlyDoneTool();
                if(recentlyDoneTool != null) {
                    // Reset flag
                    recentlyDoneTool.setRecentlyDone(false);
                }
            }

        };
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

                System.out.println("AFTER DRAG X: " + (mouseEvent.getX() - dragContext.mouseAnchorX) + " Y:" +
                        (mouseEvent.getY() - dragContext.mouseAnchorY));
                System.out.println("LAYOUT X: " + node.getLayoutX() + " Y:" + node.getLayoutY());
                getCommandStackForDrag().storeAndExecute(createMoveElementCommand());
                mouseEvent.consume();
                redrawElement();
            }
        });
    }

    /**
     *
     * @return
     */
    public Node getVisualRepresentation() {
        return visualRepresentation;
    }

    /**
     *
     * @return
     */
    public T getContainedElement() {
        return containedElement;
    }

    /**
     *
     * @return
     */
    public PmlUiExtension getPmlUiExtension() {
        return pmlUiExtension;
    }

    public Node getWrapper() {
        return wrapper;
    }

    /**
     *
     */
    public abstract void setupContainer();

    /**
     * Sets the standard effect for the {@link AbstractPlanElementContainer}.
     * This should be overwritten by a child class for individual styling. By default no effect is set.
     */
    public void setEffectToStandard() {
        setEffect(null);
    }

    public abstract Color getVisualisationColor();

    @Override
    public CommandStack getCommandStackForDrag() {
        return commandStack;
    }

    @Override
    public void redrawElement() {

    }

    @Override
    public AbstractCommand createMoveElementCommand() {
        return null;
    }

    @Override
    public void setDragged(boolean dragged) {

    }

    @Override
    public boolean wasDragged() {
        return false;
    }
}
