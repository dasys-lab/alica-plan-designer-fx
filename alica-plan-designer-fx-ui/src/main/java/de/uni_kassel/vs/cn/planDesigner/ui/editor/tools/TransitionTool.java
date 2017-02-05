package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanEditorPane;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanTab;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.TabPane;

import java.util.HashMap;
import java.util.Map;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 05.01.17.
 */
public class TransitionTool extends Tool<Transition> {

    private HashMap<EventType, EventHandler> eventHandlerMap = new HashMap<>();
    private boolean initial = true;

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
