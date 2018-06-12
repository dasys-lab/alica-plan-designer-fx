package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.serialization.CustomFileReferenceSerializer;

public class Task extends PlanElement implements IInhabitable {

    @JsonSerialize(using = CustomFileReferenceSerializer.class)
    TaskRepository taskRepository;

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
}
