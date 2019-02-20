package de.unikassel.vs.alica.planDesigner.command.create;

import de.unikassel.vs.alica.planDesigner.alicamodel.Task;
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
        // in case of a task, the model manager is working with its task repo, instead of the parent element
        modelManager.storePlanElement(Types.TASK, task, null, false);
    }

    @Override
    public void undoCommand() {
        // in case of a task, the model manager is working with its task repo, instead of the parent element
        modelManager.removedPlanElement(Types.TASK, task, null, false);
    }
}
