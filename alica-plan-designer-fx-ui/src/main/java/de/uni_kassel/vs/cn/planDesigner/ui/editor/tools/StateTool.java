package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.alica.State;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;

/**
 * The {@link StateTool} is used for adding new states to the currently edited {@link de.uni_kassel.vs.cn.planDesigner.alica.Plan}.
 */
public class StateTool implements Tool<State> {

    @Override
    public State createNewObject() {
        return getAlicaFactory().createState();
    }

    @Override
    public DragableHBox<State> createToolUI() {
        return new DragableHBox<State>(createNewObject(), this);
    }

    @Override
    public void startPhase() {

    }

    @Override
    public void endPhase() {

    }

    @Override
    public void draw() {

    }
}
