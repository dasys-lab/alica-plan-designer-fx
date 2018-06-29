package de.uni_kassel.vs.cn.planDesigner.view.repo;

import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.RepositoryTool;
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

    public void setGuiModificationHandler(IGuiModificationHandler usageHandler) {
        plansTab.setGuiModificationHandler(usageHandler);
        planTypesTab.setGuiModificationHandler(usageHandler);
        behavioursTab.setGuiModificationHandler(usageHandler);
        tasksTab.setGuiModificationHandler(usageHandler);
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
        planTypesTab.addElements(planTypes);
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

    public GuiModificationEvent handleDelete() {
        // TODO: rework
        return null;

        // RepositoryView selected
//        boolean isRepoFocused = getTabs().stream()
//                .anyMatch(e -> ((RepositoryTab) e).getRepositoryListView().focusedProperty().get());

//        if (isRepoFocused) {
//            int selectedTabIndex = getSelectionModel().getSelectedIndex();
//            PlanElement selectedPlanElement = ((RepositoryTab) repositoryTabPane
//                    .getSelectionModel()
//                    .getSelectedItem())
//                    .getRepositoryListView()
//                    .getSelectionModel().getSelectedItem().getObject();
//            editorTabPane.getTabs()
//                    .stream()
//                    .filter(e -> ((AbstractPlanTab<PlanElement>) e).getEditable().equals(selectedPlanElement))
//                    .forEach(e -> editorTabPane.getTabs().remove(e));
//            if (selectedPlanElement instanceof AbstractPlan) {
//                checkAbstractPlanUsage(commandStack, (AbstractPlan) selectedPlanElement);
//            } else if (selectedPlanElement instanceof Task) {
//                List<Pair<TaskRepository, Path>> taskRepositories = RepositoryViewModel.getInstance().getTaskRepository();
//                TaskRepository taskRepository = null;
//                for (Pair<TaskRepository, Path> pair : taskRepositories) {
//                    if (pair.getKey().getTasks().contains((Task) selectedPlanElement)) {
//                        taskRepository = pair.getKey();
//                        break;
//                    }
//                }
//                if (taskRepository != null) {
//                    isTaskUsed(commandStack, taskRepository, (Task) selectedPlanElement);
//                }
//            }
//            repositoryTabPane.init();
//            repositoryTabPane.getSelectionModel().select(selectedTabIndex);
//            return null;
//        }
//
//        return null;
    }
}
