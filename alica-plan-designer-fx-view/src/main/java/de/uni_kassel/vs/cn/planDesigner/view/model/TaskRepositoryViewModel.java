package de.uni_kassel.vs.cn.planDesigner.view.model;

import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.taskRepoTab.TaskRepositoryTab;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class TaskRepositoryViewModel extends SerializableViewModel {

    private TaskRepositoryTab taskRepositoryTab;
    private ObservableList<TaskViewModel> tasks;

    public TaskRepositoryViewModel(long id, String name, String type) {
        super(id, name, type);
        tasks = FXCollections.observableArrayList(new ArrayList<>());
        tasks.addListener(new ListChangeListener<TaskViewModel>() {
            public void onChanged(Change<? extends TaskViewModel> c) {
                if (taskRepositoryTab == null) {
                    return;
                }
                taskRepositoryTab.clearGuiContent();
                taskRepositoryTab.addElements(tasks);
            }
        });
        dirtyProperty().addListener((observable, oldValue, newValue) -> {
            this.taskRepositoryTab.setDirty(newValue);
        });
    }

    public void setTaskRepositoryTab(TaskRepositoryTab taskRepositoryTab) {
        this.taskRepositoryTab = taskRepositoryTab;
        if (taskRepositoryTab == null) {
            return;
        }

        taskRepositoryTab.clearGuiContent();
        taskRepositoryTab.addElements(tasks);
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
