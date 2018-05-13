package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.PlanDesigner;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add.AddTransitionInPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.StateContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.TerminalStateContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.PlanTab;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.Map;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 05.01.17.
 */
public class TransitionTool extends AbstractTool<Transition> {

    private HashMap<EventType, EventHandler> eventHandlerMap = new HashMap<>();
    private boolean initial = true;
    private StateContainer start;
    private StateContainer finish;
    private Cursor previousCursor;

    public TransitionTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    public Transition createNewObject() {
        return getAlicaFactory().createTransition();
    }

    @Override
    public void draw() {
        ((PlanTab)workbench.getSelectionModel().getSelectedItem()).getPlanEditorGroup().setupPlanVisualisation();
    }

    @Override
    protected Map<EventType, EventHandler> toolRequiredHandlers() {
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
                                    ((PlanTab)workbench.getSelectionModel().getSelectedItem()).getPlanEditorGroup().getPlanModelVisualisationObject(),
                                    start.getContainedElement(), finish.getContainedElement());
                            MainController.getInstance()
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
    public DragableHBox<Transition> createToolUI() {
        dragableHBox = new TransitionHBox();
        return dragableHBox;
    }

    private class TransitionHBox extends DragableHBox<Transition> {
        public TransitionHBox() {
            super(TransitionTool.this.createNewObject(), TransitionTool.this);
            setOnDragDetected(Event::consume);
            setOnMouseClicked(event -> startPhase());
        }
    }

}
