package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab;

import de.unikassel.vs.alica.planDesigner.view.model.RoleViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.TaskViewModel;
import de.unikassel.vs.alica.planDesigner.view.properties.PropertiesTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeListener;

public class TaskPriorityTableView extends PropertiesTable<TaskPriorityTableElement> {

    private RoleViewModel currentRole;
    private float priorityDefault;
    private PropertyChangeListener eventListener;

    public TaskPriorityTableView(float priorityDefault) {
        super();
        this.priorityDefault = priorityDefault;
    }

    public void updateSelectedRole(RoleViewModel item) {
        this.setCurrentRole(item);
        this.updateCells();
    }

    private void updateCells() {
        for (Object item : this.getItems()) {
            Float taskPriority = currentRole.getTaskPriority(((TaskPriorityTableElement) item).getTaskID());
            taskPriority = taskPriority == null ? priorityDefault : taskPriority;
            ((TaskPriorityTableElement) item).setPriority(String.valueOf(taskPriority));
        }
    }

    public  void setCurrentRole(RoleViewModel item) {
        this.currentRole = (RoleViewModel) item;
    }

    public void addTasks(ObservableList<TaskViewModel> taskViewModels) {
        ObservableList<TaskPriorityTableElement> taskPriorities = FXCollections.observableArrayList();
        taskViewModels.forEach(taskViewModel -> {
            TaskPriorityTableElement taskPriority = new TaskPriorityTableElement(this, taskViewModel, String.valueOf(priorityDefault));
            taskPriority.addListener(this.eventListener);
            taskPriorities.add(taskPriority);
        });
        this.setItems(taskPriorities);
    }

    public void addListener(PropertyChangeListener eventListener) {
        this.eventListener = eventListener;
    }

    public void setPriorityDefault(float priorityDefault) {
        this.priorityDefault = priorityDefault;
    }

    public RoleViewModel getCurrentRole() {
        return currentRole;
    }
}
