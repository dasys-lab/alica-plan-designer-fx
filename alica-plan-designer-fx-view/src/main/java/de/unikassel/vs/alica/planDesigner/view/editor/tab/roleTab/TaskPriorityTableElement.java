package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab;

import de.unikassel.vs.alica.planDesigner.view.model.TaskViewModel;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;

public class TaskPriorityTableElement {

    private final TaskViewModel task;
    private SimpleStringProperty taskName;
    private SimpleStringProperty priority;

    public TaskPriorityTableElement(TaskViewModel task, String priority) {
        this.task = task;
        this.taskName = new SimpleStringProperty(task.getName());
        this.priority = new SimpleStringProperty(priority);
    }

    public void addListener(InvalidationListener listener) {
        this.priority.addListener(listener);
    }

    public SimpleStringProperty priorityProperty() {
        return priority;
    }

    public SimpleStringProperty taskNameProperty() {
        return taskName;
    }

    public void setPriority(String priority) {
        this.priority.set(priority);
    }

    public TaskViewModel getTask() {
        return task;
    }

    public long getTaskID() {
        return task.getId();
    }
}
