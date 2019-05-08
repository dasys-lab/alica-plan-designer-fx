package de.uni_kassel.vs.cn.planDesigner.view.repo;

import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import javafx.scene.control.TabPane;

/**
 * Parent object for the tabs of the Repository View. Is created by loading
 * the mainWindow.fxml file and initialized by the corresponding MainWindowController.
 */
public class RepositoryTabPane extends TabPane {

    RepositoryTab plansTab;
    RepositoryTab planTypesTab;
    RepositoryTab behavioursTab;
    RepositoryTab tasksTab;

    public RepositoryTabPane() {
        TabPane planEditorTabPane = MainWindowController.getInstance().getEditorTabPane();
        RepositoryTool planTool = new RepositoryTool(planEditorTabPane);
        RepositoryTool behaviourTool = new RepositoryTool(planEditorTabPane);
        RepositoryTool planTypeTool = new RepositoryTool(planEditorTabPane);
        RepositoryTool taskTool = new RepositoryTool(planEditorTabPane);

        plansTab = new RepositoryTab("Plans",  planTool);
        planTypesTab = new RepositoryTab("PlanTypes", planTypeTool);
        behavioursTab = new RepositoryTab("Behaviours", behaviourTool);
        tasksTab = new RepositoryTab("Tasks", taskTool);

        getTabs().addAll(plansTab, planTypesTab, behavioursTab, tasksTab);
    }

    public void addPlan(ViewModelElement plan) {
        plansTab.addElement(plan);
    }

    public void addBehaviour(ViewModelElement behaviour) {
        behavioursTab.addElement(behaviour);
    }

    public void addPlanType(ViewModelElement planType) {
        planTypesTab.addElement(planType);
    }

    public void addTask(ViewModelElement task) {
        tasksTab.addElement(task);
    }
}
