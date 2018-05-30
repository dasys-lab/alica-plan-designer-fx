package de.uni_kassel.vs.cn.planDesigner.view.editor.tools.transition;

import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.EntryPointContainer;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.StateContainer;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.PlanTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.AbstractTool;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.DraggableHBox;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.Map;

public class InitTransitionTool extends AbstractTool {

    private HashMap<EventType, EventHandler> eventHandlerMap = new HashMap<>();
    private boolean initial = true;
    private EntryPointContainer start;
    private StateContainer finish;
    private Cursor previousCursor;

    public InitTransitionTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    public InitStateConnection createNewObject() {
        return new InitStateConnection();
    }

    @Override
    public void draw() {
        ((PlanTab)workbench.getSelectionModel().getSelectedItem()).getPlanEditorGroup().setupPlanVisualisation();
    }

    @Override
    protected Map<EventType, EventHandler> getCustomHandlerMap() {
        if (eventHandlerMap.isEmpty()) {
            eventHandlerMap.put(MouseEvent.MOUSE_CLICKED, event -> {
                if(((Node)event.getTarget()).getParent() instanceof EntryPointContainer) {
                    if (initial) {
                        start = (EntryPointContainer) ((Node)event.getTarget()).getParent();
                        initial = false;
                    } else {
                        endPhase();
                    }
                } else if (((Node)event.getTarget()).getParent() instanceof StateContainer && initial == false) {
                    finish = (StateContainer) ((Node)event.getTarget()).getParent();
                    initial = true;
                    SetStateForEntryPoint command = new SetStateForEntryPoint(start.getContainedElement(), finish.getContainedElement());
                    MainWindowController.getInstance()
                            .getCommandStack()
                            .storeAndExecute(command);
                } else {
                    endPhase();
                }

                event.consume();
            });

            eventHandlerMap.put(MouseEvent.MOUSE_MOVED, event -> {
                if (event.getTarget() instanceof Node == false) {
                    event.consume();
                    return;
                }
                Node target = (Node) event.getTarget();

                if (initial) {
                    if (((Node)event.getTarget()).getParent() instanceof EntryPointContainer == false) {
                        if (target.getScene().getCursor().equals(PlanDesigner.FORBIDDEN_CURSOR) == false) {
                            previousCursor = target.getScene().getCursor();
                            target.getScene().setCursor(PlanDesigner.FORBIDDEN_CURSOR);
                        }
                    } else {
                        target.getScene().setCursor(previousCursor);
                    }
                } else {
                    if (((Node)event.getTarget()).getParent() instanceof StateContainer == false) {
                        if (target.getScene().getCursor().equals(PlanDesigner.FORBIDDEN_CURSOR) == false) {
                            previousCursor = target.getScene().getCursor();
                            target.getScene().setCursor(PlanDesigner.FORBIDDEN_CURSOR);
                        }
                    } else {
                        target.getScene().setCursor(previousCursor);
                    }
                }
                event.consume();
            });
        }
        return eventHandlerMap;
    }

    @Override
    public DraggableHBox createToolUI() {
        draggableHBox = new InitTransitionTool.InitStateConnectionHBox();
        return draggableHBox;
    }

    private class InitStateConnectionHBox extends DraggableHBox {
        public InitStateConnectionHBox() {
            super("initstateconnection", InitTransitionTool.this);
            setOnDragDetected(Event::consume);
            setOnMouseClicked(event -> startPhase());
        }
    }

    /**
     * This is a pseudo class because init transitions are no real objects.
     */
    static final class InitStateConnection extends PlanElementImpl {

    }
}
