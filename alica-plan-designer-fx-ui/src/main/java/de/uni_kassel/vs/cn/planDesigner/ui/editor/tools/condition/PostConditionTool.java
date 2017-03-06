package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.condition;

import de.uni_kassel.vs.cn.planDesigner.alica.Condition;
import javafx.scene.control.TabPane;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 01.03.17.
 */
public class PostConditionTool extends AbstractConditionTool {

    public PostConditionTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    public Condition createNewObject() {
        return getAlicaFactory().createPostCondition();
    }
}
