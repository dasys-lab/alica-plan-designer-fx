package de.unikassel.vs.alica.planDesigner.view.model;

import java.util.Arrays;

public class TaskViewModel extends PlanElementViewModel {
    protected TaskRepositoryViewModel taskRepositoryViewModel;
    public TaskViewModel (long id, String name, String type) {
        super(id, name, type);

        this.uiPropertyList.clear();
        this.uiPropertyList.addAll(Arrays.asList("name", "id", "comment", "relativeDirectory"));
    }

    public TaskRepositoryViewModel getTaskRepositoryViewModel() {
        return taskRepositoryViewModel;
    }

    public void setTaskRepositoryViewModel(TaskRepositoryViewModel taskRepositoryViewModel) {
        this.taskRepositoryViewModel = taskRepositoryViewModel;
    }
}
