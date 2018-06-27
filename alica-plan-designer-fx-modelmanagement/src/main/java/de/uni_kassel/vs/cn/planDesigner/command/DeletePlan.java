package de.uni_kassel.vs.cn.planDesigner.command;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelModificationQuery;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.Types;

import java.io.File;

public class DeletePlan extends AbstractCommand {

    protected Plan plan;

    public DeletePlan(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        if (mmq.getModelElementType().equals(Types.PLAN)) {
           for(Plan currentPlan: modelManager.getPlans()) {
                if (currentPlan.getName().equals(mmq.getName())) {
                    plan = currentPlan;
                    break;
                }
           }
        }
    }

    @Override
    public void doCommand() {
        modelManager.removePlanElement(plan, Types.PLAN, true);
    }

    @Override
    public void undoCommand() {
        modelManager.addPlanElement(plan, Types.PLAN, true);
    }
}
