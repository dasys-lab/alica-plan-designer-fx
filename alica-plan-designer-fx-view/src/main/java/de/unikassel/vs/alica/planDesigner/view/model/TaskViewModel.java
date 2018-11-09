package de.unikassel.vs.alica.planDesigner.view.model;

public class TaskViewModel extends PlanElementViewModel {
    protected TaskRepositoryViewModel taskRepositoryViewModel;
    public TaskViewModel (long id, String name, String type) {
        super(id, name, type);
    }

    public TaskRepositoryViewModel getTaskRepositoryViewModel() {
        return taskRepositoryViewModel;
    }

    public void setTaskRepositoryViewModel(TaskRepositoryViewModel taskRepositoryViewModel) {
        this.taskRepositoryViewModel = taskRepositoryViewModel;
    }
}
