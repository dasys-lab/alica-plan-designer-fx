package de.uni_kassel.vs.cn.planDesigner.view.editor.tools.transition;

import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.StateContainer;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.TerminalStateContainer;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.PlanTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.DraggableHBox;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.Map;

public class TransitionTool extends AbstractTool<Transition> {

    private HashMap<EventType, EventHandler> eventHandlerMap = new HashMap<>();
    private boolean initial = true;
    private StateContainer start;
    private StateContainer finish;

    public TransitionTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    public Transition createNewObject() {
        return getAlicaFactory().createTransition();
    }

    @Override
    public void draw() {
        ((PlanTab) parentTabPane.getSelectionModel().getSelectedItem()).getPlanEditorGroup().setupPlanVisualisation();
    }

    @Override
    protected Map<EventType, EventHandler> getCustomHandlerMap() {
        if (eventHandlerMap.isEmpty()) {
            eventHandlerMap.put(MouseEvent.MOUSE_CLICKED, event -> {
                    if (initial) {
                        if(((Node)event.getTarget()).getParent() instanceof StateContainer &&
                                ((Node)event.getTarget()).getParent() instanceof TerminalStateContainer == false) {
                            start = (StateContainer) ((Node)event.getTarget()).getParent();
                            initial = false;
                        } else {
                            endPhase();
                        }
                    } else {
                        if (((Node)event.getTarget()).getParent() instanceof StateContainer) {
                            finish = (StateContainer) ((Node)event.getTarget()).getParent();
                            AddTransitionInPlan command = new AddTransitionInPlan(
                                    ((PlanTab) parentTabPane.getSelectionModel().getSelectedItem()).getPlanEditorGroup().getPlanModelVisualisationObject(),
                                    start.getModelElementId(), finish.getModelElementId());
                            MainWindowController.getInstance()
                                    .getCommandStack()
                                    .storeAndExecute(command);
                        }

                        initial = true;
                        endPhase();
                    }
            });

            eventHandlerMap.put(MouseEvent.MOUSE_MOVED, event -> {
                if (event.getTarget() instanceof Node == false) {
                    event.consume();
                    return;
                }
                Node target = (Node) event.getTarget();

                if (initial) {
                    if (((Node)event.getTarget()).getParent() instanceof StateContainer == false ||
                            ((Node)event.getTarget()).getParent() instanceof TerminalStateContainer) {
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
        draggableHBox = new TransitionHBox();
        return draggableHBox;
    }

    private class TransitionHBox extends DraggableHBox<Transition> {
        public TransitionHBox() {
            super(TransitionTool.this.createNewObject(), TransitionTool.this);
            setOnDragDetected(Event::consume);
            setOnMouseClicked(event -> startPhase());
        }
    }

}
