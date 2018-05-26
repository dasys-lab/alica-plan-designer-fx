package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.state;

import de.uni_kassel.vs.cn.planDesigner.alica.State;
import javafx.scene.control.TabPane;

import static de.uni_kassel.vs.cn.generator.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 24.02.17.
 */
public class FailureStateTool extends StateTool {

    public FailureStateTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    public State createNewObject() {
        return getAlicaFactory().createFailureState();
    }
}
