package de.uni_kassel.vs.cn.planDesigner.command.create;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Task;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskRepository;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelModificationQuery;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.Types;

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
            modelManager.addPlanElement(task, Types.TASK, false);
        } else {
            System.err.println("CreateTask: TaskRepository ID in ModelManager does not match the TaskRepository ID of the ModelModificationQuery!");
        }
    }

    @Override
    public void undoCommand() {
        modelManager.removePlanElement(task, Types.TASK, false);
    }
}
