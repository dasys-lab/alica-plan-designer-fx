package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.PlanDesigner;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.SetStateForEntryPoint;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.PlanElementImpl;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.EntryPointContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.StateContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.SynchronisationContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.TransitionContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.PlanTab;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by marci on 08.03.17.
 */
public class InitTransitionTool extends AbstractTool<InitTransitionTool.InitStateConnection> {

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
        ((PlanTab)workbench.getSelectionModel().getSelectedItem()).getPlanEditorPane().setupPlanVisualisation();
    }

    @Override
    protected Map<EventType, EventHandler> toolRequiredHandlers() {
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
                    MainController.getInstance()
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
    public DragableHBox<InitStateConnection> createToolUI() {
        dragableHBox = new InitTransitionTool.InitStateConnectionHBox();
        return dragableHBox;
    }

    private class InitStateConnectionHBox extends DragableHBox<InitStateConnection> {
        public InitStateConnectionHBox() {
            super(InitTransitionTool.this.createNewObject(), InitTransitionTool.this);
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
