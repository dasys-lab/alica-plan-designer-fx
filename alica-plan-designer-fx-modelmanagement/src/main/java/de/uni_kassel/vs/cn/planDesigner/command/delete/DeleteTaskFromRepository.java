package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Task;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelModificationQuery;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.Types;

public class DeleteTaskFromRepository extends AbstractCommand {

    ModelModificationQuery mmq;
    protected Task taskToDelete;

    public DeleteTaskFromRepository(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        this.mmq = mmq;
    }

    @Override
    public void doCommand() {
        for (Task task : modelManager.getTaskRepository().getTasks()) {
            if (task.getId() == mmq.getElementId()) {
                taskToDelete = task;
                break;
            }
        }

        // put outside the loop, in order to avoid concurrent modification exception
        if (taskToDelete != null) {
            modelManager.removePlanElement(Types.TASK, taskToDelete, null, false);
        }
    }

    @Override
    public void undoCommand() {
        modelManager.addPlanElement(Types.TASK, taskToDelete, null, false);
    }
}
