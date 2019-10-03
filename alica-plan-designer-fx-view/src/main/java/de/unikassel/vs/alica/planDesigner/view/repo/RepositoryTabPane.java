package de.unikassel.vs.alica.planDesigner.view.repo;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.controller.UsagesWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.RepositoryTool;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.scene.control.TabPane;

import java.util.List;

/**
 * Parent object for the tabs of the Repository View. Is created by loading the
 * mainWindow.fxml file and initialized by the corresponding
 * MainWindowController.
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

        plansTab = new RepositoryTab("Plans", planTool);
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
        behavioursTab.addElements(behaviours);
    }

    public void addPlanType(ViewModelElement planType) {
        planTypesTab.addElement(planType);
    }

    public void addPlanTypes(final List<ViewModelElement> planTypes) {
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
        RepositoryTab selectedTab = (RepositoryTab) this.getSelectionModel().getSelectedItem();
        boolean focused = this.isFocused() || selectedTab.getContent().isFocused();
        if (!focused) {
            return null;
        }

        ViewModelElement selectedItem = selectedTab.getSelectedItem();
        if (selectedItem.getType().equals(Types.TASK)) {
            // TODO: Implement deletion of Tasks
            return null;
        } else {
            IGuiModificationHandler guiModificationHandler = MainWindowController.getInstance()
                    .getGuiModificationHandler();
            List<ViewModelElement> usages = guiModificationHandler.getUsages(selectedItem);
            if (!usages.isEmpty()) {
                UsagesWindowController.createUsagesWindow(usages,
                        I18NRepo.getInstance().getString("label.usage.nodelete"), guiModificationHandler);
                return null;
            }
            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.DELETE_ELEMENT, selectedItem.getType(),
                    selectedItem.getName());
            event.setElementId(selectedItem.getId());
            return event;
        }
    }
}
