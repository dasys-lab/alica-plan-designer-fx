package de.unikassel.vs.alica.planDesigner.command.create;

import de.unikassel.vs.alica.planDesigner.alicamodel.Task;
import de.unikassel.vs.alica.planDesigner.alicamodel.TaskRepository;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class CreateTask extends AbstractCommand {
    Task task;
    ModelModificationQuery mmq;

    public CreateTask(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        this.mmq = mmq;
        if (mmq.getElementType().equals(Types.TASK)) {
            this.task = new Task();
            this.task.setName(mmq.getName());
        } else {
            System.err.println("CreateTask: Type does not match command!");
        }
    }

    @Override
    public void doCommand() {
        TaskRepository taskRepository = modelManager.getTaskRepository();
        if (taskRepository.getId() == mmq.getParentId()) {
            modelManager.addPlanElement(Types.TASK, task, taskRepository, false);
        } else {
            System.err.println("CreateTask: TaskRepository ID in ModelManager does not match the TaskRepository ID of the ModelModificationQuery!");
        }
    }

    @Override
    public void undoCommand() {
        TaskRepository taskRepository = modelManager.getTaskRepository();
        if (taskRepository.getId() == mmq.getParentId()) {
            modelManager.removePlanElement(Types.TASK, task, taskRepository, false);
        } else {
            System.err.println("CreateTask: TaskRepository ID in ModelManager does not match the TaskRepository ID of the ModelModificationQuery (Undo)!");
        }
    }
}
