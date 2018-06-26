package de.uni_kassel.vs.cn.planDesigner.view.repo;

import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.RepositoryTool;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IShowUsageHandler;
import javafx.scene.control.TabPane;

import java.util.List;

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

    public void setShowUsageHandler(IShowUsageHandler usageHandler) {
        plansTab.setShowUsageHandler(usageHandler);
        planTypesTab.setShowUsageHandler(usageHandler);
        behavioursTab.setShowUsageHandler(usageHandler);
        tasksTab.setShowUsageHandler(usageHandler);
    }

    public void addPlan(ViewModelElement plan) {
        plansTab.addElement(plan);
    }

    public void addPlans(List<ViewModelElement> plans) {
        plansTab.addElements(plans);
    }

    public void addBehaviour(ViewModelElement behaviour) {
        behavioursTab.addElement(behaviour);
    }

    public void addBehaviours(List<ViewModelElement> behaviours) {
        plansTab.addElements(behaviours);
    }

    public void addPlanType(ViewModelElement planType) {
        planTypesTab.addElement(planType);
    }

    public void addPlanTypes(List<ViewModelElement> planTypes) {
        plansTab.addElements(planTypes);
    }

    public void addTask(ViewModelElement task) {
        tasksTab.addElement(task);
    }

    public void addTasks(List<ViewModelElement> tasks) {
        tasksTab.addElements(tasks);
    }

    public void clearGuiContent() {
        plansTab.clearGuiContent();
        planTypesTab.clearGuiContent();
        behavioursTab.clearGuiContent();
        tasksTab.clearGuiContent();
    }

    public void clearPlansTab() {
        plansTab.clearGuiContent();
    }

    public void clearPlanTypesTab() {
        planTypesTab.clearGuiContent();
    }

    public void clearBehavioursTab() {
        behavioursTab.clearGuiContent();
    }

    public void clearTasksTab() {
        tasksTab.clearGuiContent();
    }
}
