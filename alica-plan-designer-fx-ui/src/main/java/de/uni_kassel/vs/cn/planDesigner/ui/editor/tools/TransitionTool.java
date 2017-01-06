package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanEditorPane;
import javafx.event.EventHandler;
import javafx.event.EventType;

import java.util.HashMap;
import java.util.Map;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 05.01.17.
 */
public class TransitionTool extends Tool<Transition> {

    public TransitionTool(PlanEditorPane workbench) {
        super(workbench);
    }

    @Override
    public Transition createNewObject() {
        return getAlicaFactory().createTransition();
    }

    @Override
    public void draw() {

    }

    @Override
    protected Map<EventType, EventHandler> toolRequiredHandlers() {
        return new HashMap<>();
    }
}
