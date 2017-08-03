package de.uni_kassel.vs.cn.planDesigner.ui.repo;

import de.uni_kassel.vs.cn.planDesigner.alica.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.util.AllAlicaFiles;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.AbstractPlanTool;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 25.11.16.
 */
public class RepositoryTabPane extends TabPane {

    public RepositoryTabPane() {
        getTabs().clear();
    }

    public void init() {
        getTabs().clear();
        getTabs().setAll(createAllRepoTabs());
    }

    private Tab[] createAllRepoTabs() {
        AbstractPlanTool planTool = new AbstractPlanTool(MainController.getInstance().getEditorTabPane()) {
            @Override
            public AbstractPlan createNewObject() {
                return EMFModelUtils.getAlicaFactory().createPlan();
            }
        };

        AbstractPlanTool behaviourTool = new AbstractPlanTool(MainController.getInstance().getEditorTabPane()) {
            @Override
            public AbstractPlan createNewObject() {
                return EMFModelUtils.getAlicaFactory().createBehaviour();
            }
        };

        AbstractPlanTool planTypeTool = new AbstractPlanTool(MainController.getInstance().getEditorTabPane()) {
            @Override
            public AbstractPlan createNewObject() {
                return EMFModelUtils.getAlicaFactory().createPlanType();
            }
        };

        return new Tab[]{new RepositoryTab<>(AllAlicaFiles.getInstance().getPlans(), planTool, getAlicaFactory().createPlan().eClass().getName()),
                new RepositoryTab<>(AllAlicaFiles.getInstance().getPlanTypes(), planTypeTool, getAlicaFactory().createPlanType().eClass().getName()),
                new RepositoryTab<>(AllAlicaFiles.getInstance().getBehaviours(), behaviourTool, getAlicaFactory().createBehaviour().eClass().getName()),
                new RepositoryTab<>(AllAlicaFiles.getInstance().getTasks(), behaviourTool, getAlicaFactory().createTask().eClass().getName())};
    }
}
