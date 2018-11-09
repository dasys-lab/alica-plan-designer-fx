package de.unikassel.vs.alica.planDesigner.alicamodel;

public class Task extends PlanElement{

    TaskRepository taskRepository;

    public Task() {
        super();
        this.comment.addListener((observable, oldValue, newValue) -> {
            // catches the construction of a new task
            if (taskRepository != null) {
                taskRepository.setDirty(true);
            }
        });
        this.name.addListener((observable, oldValue, newValue) -> {
            // catches the construction of a new task
            if (taskRepository != null) {
                taskRepository.setDirty(true);
            }
        });
    }

    public Task (long id) {
        this.id = id;
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
}
