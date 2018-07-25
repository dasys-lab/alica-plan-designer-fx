package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelModificationQuery;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.Types;

import java.lang.reflect.Type;

public class DeletePlan extends AbstractCommand {

    protected Plan plan;

    public DeletePlan(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        plan = (Plan) modelManager.getPlanElement(mmq.getElementId());
    }

    @Override
    public void doCommand() {
        if (plan == null) {
            return;
        }
        modelManager.removePlanElement(plan, Types.PLAN, true);
    }

    @Override
    public void undoCommand() {
        if (plan == null) {
            return;
        }
        if (!plan.getMasterPlan()) {
            modelManager.addPlanElement(plan, Types.PLAN, true);
        } else {
            modelManager.addPlanElement(plan, Types.MASTERPLAN, true);
        }
    }
}
