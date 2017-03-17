package de.uni_kassel.vs.cn.planDesigner.ui.repo;

import de.uni_kassel.vs.cn.planDesigner.PlanDesigner;
import de.uni_kassel.vs.cn.planDesigner.alica.util.AllAlicaFiles;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.AbstractPlanTool;
import javafx.scene.control.TabPane;

/**
 * Created by marci on 25.11.16.
 */
public class RepositoryTabPane extends TabPane {

    private AbstractPlanTool abstractPlanTool;

    public RepositoryTabPane() {
        getTabs().clear();
    }

    public void init() {
        abstractPlanTool = new AbstractPlanTool(MainController.getInstance().getEditorTabPane());
        getTabs().clear();
        getTabs().addAll(new RepositoryTab<>(AllAlicaFiles.getInstance().getPlans(), abstractPlanTool),
                new RepositoryTab<>(AllAlicaFiles.getInstance().getPlanTypes(),abstractPlanTool) ,
                new RepositoryTab<>(AllAlicaFiles.getInstance().getBehaviours(), abstractPlanTool),
                new RepositoryTab<>(AllAlicaFiles.getInstance().getTasks(), abstractPlanTool));


    }
}
