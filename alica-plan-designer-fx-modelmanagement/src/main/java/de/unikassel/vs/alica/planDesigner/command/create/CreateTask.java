package de.unikassel.vs.alica.planDesigner.command.create;

import de.unikassel.vs.alica.planDesigner.alicamodel.Task;
import de.unikassel.vs.alica.planDesigner.alicamodel.TaskRepository;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class CreateTask extends Command {
    Task task;
    TaskRepository taskRepository;

    public CreateTask(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.taskRepository = (TaskRepository) modelManager.getPlanElement(mmq.getParentId());
        this.task = createTask(this.taskRepository);
    }

    protected Task createTask(TaskRepository taskRepository) {
        Task task = new Task();
        task.setName(mmq.getName());
        task.setTaskRepository(taskRepository);
        return task;
    }

    @Override
    public void doCommand() {
        this.taskRepository.addTask(task);
        modelManager.storePlanElement(Types.TASK, this.task,  false);
        this.fireEvent(ModelEventType.ELEMENT_CREATED, this.task);
    }

    @Override
    public void undoCommand() {
        this.taskRepository.removeTask(task);
        modelManager.dropPlanElement(Types.TASK, this.task, false);
        this.fireEvent(ModelEventType.ELEMENT_DELETED, this.task);
    }
}
