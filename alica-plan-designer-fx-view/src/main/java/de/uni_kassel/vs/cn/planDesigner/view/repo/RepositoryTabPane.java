package de.uni_kassel.vs.cn.planDesigner.view.repo;

import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.RepositoryTool;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class RepositoryTabPane extends TabPane {

//    public RepositoryTabPane() {
//        getTabs().clear();
//    }

    public void init() {
        getTabs().clear();
        getTabs().setAll(createAllRepoTabs());
    }

    private Tab[] createAllRepoTabs() {
        RepositoryTool planTool = new RepositoryTool(MainWindowController.getInstance().getEditorTabPane()) {

        };

        RepositoryTool behaviourTool = new RepositoryTool(MainWindowController.getInstance().getEditorTabPane()) {

        };

        RepositoryTool planTypeTool = new RepositoryTool(MainWindowController.getInstance().getEditorTabPane()) {

        };

        return new Tab[]{new RepositoryTab<>(RepositoryViewModel.getInstance().getPlans(), planTool, "plan"),
                new RepositoryTab<>(RepositoryViewModel.getInstance().getPlanTypes(), planTypeTool, "planType"),
                new RepositoryTab<>(RepositoryViewModel.getInstance().getBehaviours(), behaviourTool, "behaviour"),
                new RepositoryTab<>(RepositoryViewModel.getInstance().getTasks(), new TaskTool(), "task")};
    }

    public class TaskTool extends RepositoryTool {
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
