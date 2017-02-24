package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add.AddTransitionInPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.PlanTab;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.StateContainer;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.Map;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 05.01.17.
 */
public class TransitionTool extends Tool<Transition> {

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
        ((PlanTab)workbench.getSelectionModel().getSelectedItem()).getPlanEditorPane().setupPlanVisualisation();
    }

    @Override
    protected Map<EventType, EventHandler> toolRequiredHandlers() {
        if (eventHandlerMap.isEmpty()) {
            eventHandlerMap.put(MouseEvent.MOUSE_CLICKED, event -> {
                if(((Node)event.getTarget()).getParent() instanceof StateContainer) {
                    if (initial) {
                        start = (StateContainer) ((Node)event.getTarget()).getParent();
                        initial = false;
                    } else {
                        finish = (StateContainer) ((Node)event.getTarget()).getParent();
                        initial = true;
                        AddTransitionInPlan command = new AddTransitionInPlan(
                                ((PlanTab)workbench.getSelectionModel().getSelectedItem()).getPlanEditorPane().getPlanModelVisualisationObject(),
                                start.getContainedElement(), finish.getContainedElement());
                        MainController.getInstance()
                                .getCommandStack()
                                .storeAndExecute(command);
                        endPhase();
                    }
                }
            });
        }
        return eventHandlerMap;
    }

    @Override
    public DragableHBox<Transition> createToolUI() {
        return new TransitionHBox();
    }

    private class TransitionHBox extends DragableHBox<Transition> {
        public TransitionHBox() {
            super(TransitionTool.this.createNewObject(), TransitionTool.this);
            setOnDragDetected(Event::consume);
            setOnMouseClicked(event -> startPhase());
        }
    }

}
