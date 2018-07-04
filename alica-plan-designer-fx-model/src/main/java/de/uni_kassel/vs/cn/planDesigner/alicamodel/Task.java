package de.uni_kassel.vs.cn.planDesigner.alicamodel;

public class Task extends PlanElement{

    TaskRepository taskRepository;

    public Task() {
        super();
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
