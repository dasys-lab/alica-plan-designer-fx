package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab;

import de.unikassel.vs.alica.planDesigner.view.model.TaskViewModel;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TaskPriorityTableElement {

    private final TaskPriorityTableView tableView;
    private final TaskViewModel task;
    private SimpleStringProperty taskName;
    private SimpleStringProperty priority;

    private InvalidationListener listener;

    public TaskPriorityTableElement(TaskPriorityTableView tableView, TaskViewModel task, String priority) {
        this.tableView = tableView;
        this.task = task;
        this.taskName = new SimpleStringProperty(task.getName());
        this.priority = new SimpleStringProperty(priority);
    }

    public void addListener(PropertyChangeListener listener) {
        this.listener = observable -> listener.propertyChange(new PropertyChangeEvent(
                tableView.getCurrentRole(), "taskPriority", task.getId(),
                ((StringProperty)observable).getValue()));
    }

    public SimpleStringProperty priorityProperty() {
        return priority;
    }

    public SimpleStringProperty taskNameProperty() {
        return taskName;
    }

    public void setPriority(String priority) {
        this.priority.set(priority);
        this.priority.addListener(this.listener);
    }

    public void setStartPriority(String priority) {
        this.priority.set(priority);
    }

    public TaskViewModel getTask() {
        return task;
    }

    public long getTaskID() {
        return task.getId();
    }

    public void removeListener() {
        this.priority.removeListener(this.listener);
    }
}
