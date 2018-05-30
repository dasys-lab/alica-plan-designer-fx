package de.uni_kassel.vs.cn.planDesigner.view.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.StateContainer;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.TerminalStateContainer;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.PlanTab;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseDragEvent;

import java.util.HashMap;
import java.util.Map;

public class RepositoryTool extends AbstractTool {

    private long modelElementId;
    private Map<EventType, EventHandler> eventHandlerMap = new HashMap<>();
    private Cursor previousCursor;

    public RepositoryTool(TabPane workbench) {
        super(workbench);
    }

    public long getModelElementId() {
        return modelElementId;
    }

    public void setModelElementId(long modelElementId) {
        this.modelElementId = modelElementId;
    }

    @Override
    public void draw() {
        if (workbench != null && workbench.getSelectionModel().getSelectedItem() != null) {
            ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getPlanEditorGroup().setupPlanVisualisation();
        }
    }

    @Override
    protected Map<EventType, EventHandler> toolRequiredHandlers() {
        if (eventHandlerMap.isEmpty()) {
            initHandlerMap();
        }
        return eventHandlerMap;
    }

    private void initHandlerMap () {
        previousCursor = workbench.getScene().getCursor();
        eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_OVER, new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                if (event.getTarget() == null || ((Node) event.getTarget()).getParent() instanceof StateContainer == false ||
                        ((Node) event.getTarget()).getParent() instanceof TerminalStateContainer) {
                    if (workbench.getScene().getCursor().equals(PlanDesigner.FORBIDDEN_CURSOR) == false) {
                        previousCursor = workbench.getScene().getCursor();
                        workbench.getScene().setCursor(PlanDesigner.FORBIDDEN_CURSOR);
                    }
                } else {
                    workbench.getScene().setCursor(previousCursor);
                }
                event.consume();
            }
        });


        eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                if (event.getTarget() != null && ((Node) event.getTarget()).getParent() instanceof StateContainer &&
                        ((Node) event.getTarget()).getParent() instanceof TerminalStateContainer == false) {
                    StateContainer stateContainer = (StateContainer) ((Node) event.getTarget()).getParent();
                    AddAbstractPlanToState command = new AddAbstractPlanToState(modelElementId, stateContainer.getContainedElement());
                    MainController.getInstance()
                            .getCommandStack()
                            .storeAndExecute(command);
                    draw();
                }

                endPhase();
            }
        });
    }


}
