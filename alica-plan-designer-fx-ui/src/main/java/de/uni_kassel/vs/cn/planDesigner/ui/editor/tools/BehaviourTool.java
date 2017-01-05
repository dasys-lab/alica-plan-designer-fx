package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.alica.Behaviour;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 05.01.17.
 */
public class BehaviourTool implements Tool<Behaviour> {
    @Override
    public Behaviour createNewObject() {
        return getAlicaFactory().createBehaviour();
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
