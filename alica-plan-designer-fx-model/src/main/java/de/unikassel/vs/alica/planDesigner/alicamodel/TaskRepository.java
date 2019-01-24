package de.unikassel.vs.alica.planDesigner.alicamodel;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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

    @Override
    public void registerDirtyFlag() {
        super.registerDirtyFlag();
        tasks.addListener(new ListChangeListener<Task>() {
            @Override
            public void onChanged(Change<? extends Task> change) {
                while (change.next()) {
                    setDirty(true);
                }
            }
        });
        for (Task task : tasks) {
            task.registerDirtyFlag();
        }
    }
}
