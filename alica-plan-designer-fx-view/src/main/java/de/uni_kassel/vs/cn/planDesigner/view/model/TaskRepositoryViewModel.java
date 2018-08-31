package de.uni_kassel.vs.cn.planDesigner.view.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class TaskRepositoryViewModel extends SerializableViewModel {

    private ObservableList<TaskViewModel> tasks;

    public TaskRepositoryViewModel(long id, String name, String type) {
        super(id, name, type);
        tasks = FXCollections.observableArrayList(new ArrayList<>());
    }

    public void addTask(TaskViewModel task) {
        if (!this.tasks.contains(task)) {
            this.tasks.add(task);
        }
    }

    public void removeTask(long id) {
        for(ViewModelElement task : tasks) {
            if(task.getId() == id) {
                this.tasks.remove(task);
                break;
            }
        }
    }

    public ObservableList<TaskViewModel> getTaskViewModels() {
        return tasks;
    }
}
