package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import java.util.ArrayList;

public class TaskRepository extends SerializablePlanElement {

    protected ArrayList<Task> tasks;
    protected Task defaultTask;

    public TaskRepository() {
        tasks = new ArrayList<>();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public Task getDefaultTask() {
        return defaultTask;
    }

    public void setDefaultTask(Task defaultTask) {
        this.defaultTask = defaultTask;
    }
}
