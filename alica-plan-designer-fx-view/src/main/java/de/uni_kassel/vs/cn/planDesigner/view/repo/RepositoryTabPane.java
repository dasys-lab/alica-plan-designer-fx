package de.uni_kassel.vs.cn.planDesigner.view.repo;

import de.uni_kassel.vs.cn.planDesigner.alica.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.AbstractPlanTool;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

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

        return new Tab[]{new RepositoryTab<>(RepositoryViewModel.getInstance().getPlans(), planTool, getAlicaFactory().createPlan().eClass().getName()),
                new RepositoryTab<>(RepositoryViewModel.getInstance().getPlanTypes(), planTypeTool, getAlicaFactory().createPlanType().eClass().getName()),
                new RepositoryTab<>(RepositoryViewModel.getInstance().getBehaviours(), behaviourTool, getAlicaFactory().createBehaviour().eClass().getName()),
                new RepositoryTab<>(RepositoryViewModel.getInstance().getTasks(), new TaskTool(), getAlicaFactory().createTask().eClass().getName())};
    }

    public class TaskTool extends AbstractPlanTool {
        public TaskTool() {
            super(RepositoryTabPane.this);
        }

        @Override
        public void startPhase() {
        }

        @Override
        public void endPhase() {
        }

        @Override
        public AbstractPlan createNewObject() {
            return EMFModelUtils.getAlicaFactory().createBehaviour();
        }
    }
}
