package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;

public class TaskPriorityTableElement {

    private SimpleStringProperty task;
    private SimpleStringProperty priority;
    private long taskID;

    public TaskPriorityTableElement(long taskID, String task, String priority) {
        this.taskID = taskID;
        this.task = new SimpleStringProperty(task);
        this.priority = new SimpleStringProperty(priority);
    }

    public void addListener(InvalidationListener listener) {
        this.priority.addListener(listener);
    }

    public SimpleStringProperty priorityProperty() {
        return priority;
    }

    public SimpleStringProperty taskProperty() {
        return task;
    }

    public void setPriority(String priority) {
        this.priority.set(priority);
    }

    public long getTaskID() {
        return taskID;
    }
}
