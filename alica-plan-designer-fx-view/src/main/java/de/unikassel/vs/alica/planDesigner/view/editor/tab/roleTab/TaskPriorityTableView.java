package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab;

import de.unikassel.vs.alica.planDesigner.view.model.RoleViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.TaskViewModel;
import de.unikassel.vs.alica.planDesigner.view.properties.PropertiesTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskPriorityTableView extends PropertiesTable<TaskPriority> {

    private RoleViewModel currentRole;

    public void updateSelectedRole(RoleViewModel item) {
        this.setCurrentRole(item);
        this.updateCells();
    }

    private void updateCells() {

        for (Object item : this.getItems()) {
            TaskPriority taskPriority = (TaskPriority)item;
            taskPriority.setPriority(String.valueOf(currentRole.getTaskPriority(taskPriority.getTaskID())));
        }
    }

    public  void setCurrentRole(RoleViewModel item) {
        this.currentRole = (RoleViewModel) item;
    }

    public RoleViewModel getCurrentRole() {
        return currentRole;
    }

    public void addTasks(ObservableList<TaskViewModel> taskViewModels) {
        ObservableList<TaskPriority> taskPriorities = FXCollections.observableArrayList();;
        taskViewModels.forEach(taskViewModel -> {
            TaskPriority taskPriority = new TaskPriority(taskViewModel.getId(), taskViewModel.getName(), "");
            taskPriority.addListener(observable -> {
                currentRole.setTaskPriority(taskPriority.getTaskID(), ((SimpleStringProperty)observable).getValue());
            });
            taskPriorities.add(taskPriority);
        });
        this.setItems(taskPriorities);

    }
}
