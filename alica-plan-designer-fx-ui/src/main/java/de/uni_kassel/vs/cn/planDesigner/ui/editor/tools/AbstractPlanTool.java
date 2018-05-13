package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.PlanDesigner;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add.AddAbstractPlanToState;
import de.uni_kassel.vs.cn.planDesigner.alica.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.StateContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.TerminalStateContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.PlanTab;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseDragEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by marci on 17.03.17.
 */
public class AbstractPlanTool extends AbstractTool<AbstractPlan> {

    private AbstractPlan activeElement;
    private Map<EventType, EventHandler> eventHandlerMap = new HashMap<>();
    private Cursor previousCursor;

    private Node visualRepresentation;

    public AbstractPlanTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    public AbstractPlan createNewObject() {
        return null;
    }

    @Override
    public void draw() {
        if (workbench != null && workbench.getSelectionModel().getSelectedItem() != null) {
            ((PlanTab)workbench.getSelectionModel().getSelectedItem()).getPlanEditorGroup().setupPlanVisualisation();
        }
    }

    @Override
    protected Map<EventType, EventHandler> toolRequiredHandlers() {
        if (eventHandlerMap.isEmpty()) {
            previousCursor = workbench.getScene().getCursor();
            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_OVER, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (event.getTarget() == null || ((Node)event.getTarget()).getParent() instanceof StateContainer == false ||
                            ((Node)event.getTarget()).getParent() instanceof TerminalStateContainer) {
                        if (workbench.getScene().getCursor().equals(PlanDesigner.FORBIDDEN_CURSOR) == false) {
                            previousCursor = workbench.getScene().getCursor();
                            workbench.getScene().setCursor(PlanDesigner.FORBIDDEN_CURSOR);
                        }
                    } else {
                        workbench.getScene().setCursor(previousCursor);
                    }
                    visualRepresentation.setLayoutX(event.getX());
                    visualRepresentation.setLayoutY(event.getY());
                    System.out.println("X: " + event.getX() + " Y: " + event.getY());
                    event.consume();
                }
            });
            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_ENTERED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (event.getGestureSource() != workbench && visualRepresentation == null) {
                        ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getPlanEditorGroup().getChildren().add(visualRepresentation);
                    }
                    event.consume();
                }
            });

            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_EXITED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (workbench.getSelectionModel().getSelectedItem() != null) {
                        ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getPlanEditorGroup().getChildren().remove(visualRepresentation);
                    }
                }
            });


            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (event.getTarget() != null && ((Node)event.getTarget()).getParent() instanceof StateContainer &&
                            ((Node)event.getTarget()).getParent() instanceof TerminalStateContainer == false) {
                        StateContainer stateContainer = (StateContainer) ((Node) event.getTarget()).getParent();
                        ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getPlanEditorGroup().getChildren().remove(visualRepresentation);
                        AddAbstractPlanToState command = new AddAbstractPlanToState(activeElement, stateContainer.getContainedElement());
                        MainController.getInstance()
                                .getCommandStack()
                                .storeAndExecute(command);
                        draw();
                    }

                    endPhase();
                }
            });
        }
        return eventHandlerMap;
    }

    public AbstractPlan getActiveElement() {
        return activeElement;
    }

    public void setActiveElement(AbstractPlan activeElement) {
        this.activeElement = activeElement;
    }

    public void setVisualRepresentation(Node visualRepresentation) {
        this.visualRepresentation = visualRepresentation;
    }

    public void setDragableHBox(DragableHBox<AbstractPlan> dragableHBox) {
        this.dragableHBox = dragableHBox;
    }
}
