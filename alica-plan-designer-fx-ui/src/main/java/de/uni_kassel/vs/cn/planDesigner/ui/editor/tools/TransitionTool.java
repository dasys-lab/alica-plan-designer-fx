package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.alica.Transition;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 05.01.17.
 */
public class TransitionTool implements Tool<Transition> {
    @Override
    public Transition createNewObject() {
        return getAlicaFactory().createTransition();
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
