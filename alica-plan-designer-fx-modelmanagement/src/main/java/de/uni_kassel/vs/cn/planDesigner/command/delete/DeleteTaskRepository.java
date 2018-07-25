package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskRepository;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelModificationQuery;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.Types;

public class DeleteTaskRepository extends AbstractCommand {

    protected TaskRepository taskRepository;

    public DeleteTaskRepository(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
//        plan = null;
//        if (mmq.getAbsoluteDirectory() != null) {
//            String relativeDirectory = modelManager.makeRelativePlansDirectory(mmq.getAbsoluteDirectory(), mmq.getName());
//            for (Plan currentPlan : modelManager.getPlans()) {
//                if (currentPlan.getName().equals(mmq.getName()) && currentPlan.getRelativeDirectory().equals(relativeDirectory)) {
//                    plan = currentPlan;
//                    break;
//                }
//            }
//        } else {
        taskRepository = (TaskRepository) modelManager.getPlanElement(mmq.getElementId());
//        }
    }

    @Override
    public void doCommand() {
        if (taskRepository == null) {
            return;
        }
        modelManager.removePlanElement(taskRepository, Types.PLAN, true);
    }

    @Override
    public void undoCommand() {
        if (taskRepository == null) {
            return;
        }
            modelManager.addPlanElement(taskRepository, Types.TASKREPOSITORY, true);
    }
}
