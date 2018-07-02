package de.uni_kassel.vs.cn.planDesigner.view.editor.tab.taskRepoTab;

import de.uni_kassel.vs.cn.planDesigner.common.ViewModelElement;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class TaskRepositoryViewModel {
    private ObservableList<ViewModelElement> tasks;
    private TaskRepositoryTab taskRepositoryTab;
    private boolean isDirty;

    public TaskRepositoryViewModel() {
        tasks = FXCollections.observableArrayList(new ArrayList<>());
        tasks.addListener(new ListChangeListener<ViewModelElement>() {
            @Override
            public void onChanged(Change<? extends ViewModelElement> c) {
                if (taskRepositoryTab == null)
                {
                    return;
                }
                taskRepositoryTab.clearGuiContent();
                taskRepositoryTab.addElements(tasks);
                if (isDirty) {
                    taskRepositoryTab.setDirty();
                }
            }
        });
        isDirty = false;
    }

    public void setTaskRepositoryTab (TaskRepositoryTab taskRepositoryTab) {
        this.taskRepositoryTab = taskRepositoryTab;
        initGuiContent();
    }

    public void initGuiContent() {
        if (taskRepositoryTab == null) {
            return;
        }

        taskRepositoryTab.clearGuiContent();
        taskRepositoryTab.addElements(tasks);
        if (this.isDirty) {
            this.taskRepositoryTab.setDirty();
        }
    }

    public void setDirty(boolean dirty) {
        this.isDirty = dirty;
    }

    public void addTask(ViewModelElement task) {
        this.tasks.add(task);
    }

    public void removeTask(ViewModelElement task) {
        tasks.remove(task);
    }

    public void clearTasks() {
        tasks.clear();
    }
}
