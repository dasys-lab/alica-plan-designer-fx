package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.alica.EntryPoint;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 05.01.17.
 */
public class EntryPointTool implements Tool<EntryPoint> {
    @Override
    public EntryPoint createNewObject() {
        return getAlicaFactory().createEntryPoint();
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
