package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab;

import de.unikassel.vs.alica.planDesigner.view.model.RoleViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.TaskViewModel;
import de.unikassel.vs.alica.planDesigner.view.properties.PropertiesTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeListener;

public class TaskPriorityTableView extends PropertiesTable<TaskPriorityTableElement> {

    private RoleViewModel currentRole;
    private float defaultPriority;
    private boolean defaultRoleSet;
    private PropertyChangeListener eventListener;

    public TaskPriorityTableView(float defaultPriority) {
        super();
        this.defaultPriority = defaultPriority;
    }

    public void updateSelectedRole(RoleViewModel item) {
        this.setCurrentRole(item);
        this.updateCells();
    }

    private void updateCells() {
        for (Object item : this.getItems()) {
            Float taskPriority = currentRole.getTaskPriority(((TaskPriorityTableElement) item).getTaskID());
            taskPriority = taskPriority == null ? defaultPriority : taskPriority;
            ((TaskPriorityTableElement) item).removeListener();
            ((TaskPriorityTableElement) item).setPriority(String.valueOf(taskPriority));
        }
    }

    public  void setCurrentRole(RoleViewModel item) {
        this.currentRole = (RoleViewModel) item;
    }

    public void addTasks(ObservableList<TaskViewModel> taskViewModels) {
        ObservableList<TaskPriorityTableElement> taskPriorities = FXCollections.observableArrayList();
        taskViewModels.forEach(taskViewModel -> {
            TaskPriorityTableElement taskPriority = new TaskPriorityTableElement(this, taskViewModel, String.valueOf(defaultPriority));
            taskPriority.addListener(this.eventListener);
            taskPriorities.add(taskPriority);
        });
        this.setItems(taskPriorities);
    }

    public void addListener(PropertyChangeListener eventListener) {
        this.eventListener = eventListener;
    }

    public float getDefaultPriority() {
        return defaultPriority;
    }
    public void setDefaultPriority(float defaultPriority) {
        this.defaultPriority = defaultPriority;
    }
    public boolean getDefaultRoleSet() {
        return defaultRoleSet;
    }
    public void setDefaultRoleSet(boolean defaultRoleSet) {
        this.defaultRoleSet = defaultRoleSet;
    }


    public RoleViewModel getCurrentRole() {
        return currentRole;
    }
}
