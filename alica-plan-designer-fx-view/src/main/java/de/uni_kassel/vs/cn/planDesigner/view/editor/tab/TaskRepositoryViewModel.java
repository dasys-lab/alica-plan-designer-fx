package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.view.repo.ViewModelElement;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class TaskRepositoryViewModel {
    private ObservableList<ViewModelElement> tasks;
    private TaskRepositoryTab taskRepositoryTab;

    public TaskRepositoryViewModel() {
        tasks = FXCollections.observableArrayList(new ArrayList<>());
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
        tasks.addListener(new ListChangeListener<ViewModelElement>() {
            @Override
            public void onChanged(Change<? extends ViewModelElement> c) {
                taskRepositoryTab.clearGuiContent();
                taskRepositoryTab.addElements(tasks);
            }
        });
    }

    public void addTask(ViewModelElement task) {
        this.tasks.add(task);
    }

    public void removeTask(ViewModelElement task) {
        for (ViewModelElement element : tasks) {
            if (element.getId() == task.getId()) {
                tasks.remove(element);
            }
        }
    }
}
