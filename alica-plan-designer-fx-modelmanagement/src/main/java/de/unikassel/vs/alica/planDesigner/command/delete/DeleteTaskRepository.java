package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.TaskRepository;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class DeleteTaskRepository extends AbstractCommand {

    protected TaskRepository taskRepository;

    public DeleteTaskRepository(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        taskRepository = (TaskRepository) modelManager.getPlanElement(mmq.getElementId());
    }

    @Override
    public void doCommand() {
        if (taskRepository == null) {
            return;
        }
        modelManager.removedPlanElement(Types.PLAN, taskRepository, null, true);
    }

    @Override
    public void undoCommand() {
        if (taskRepository == null) {
            return;
        }
            modelManager.storePlanElement(Types.TASKREPOSITORY, taskRepository, null, true);
    }
}
