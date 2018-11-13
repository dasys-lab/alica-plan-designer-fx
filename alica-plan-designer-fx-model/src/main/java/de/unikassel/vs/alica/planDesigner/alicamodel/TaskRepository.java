package de.unikassel.vs.alica.planDesigner.alicamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskRepository extends SerializablePlanElement {

    protected ObservableList<Task> tasks;
    protected Task defaultTask;

    public TaskRepository() {
        tasks = FXCollections.observableArrayList();

    }

    public ObservableList<Task> getTasks() {
        return tasks;
    }

    public Task getDefaultTask() {
        return defaultTask;
    }

    public void setDefaultTask(Task defaultTask) {
        this.defaultTask = defaultTask;
    }
}
