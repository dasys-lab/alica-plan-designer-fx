package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.state;

import de.uni_kassel.vs.cn.planDesigner.alica.State;
import javafx.scene.control.TabPane;

import static de.uni_kassel.vs.cn.planDesigner.emfUtil.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 24.02.17.
 */
public class SuccessStateTool extends StateTool {
    public SuccessStateTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    public State createNewObject() {
        return getAlicaFactory().createSuccessState();
    }
}
