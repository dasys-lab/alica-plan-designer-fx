package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class DeletePlan extends Command {

    protected Plan plan;

    public DeletePlan(ModelManager manager, ModelModificationQuery mmq) {
        super(manager, mmq);
        plan = (Plan) manager.getPlanElement(mmq.getElementId());
    }

    @Override
    public void doCommand() {
        if (plan == null) {
            return;
        }
        modelManager.dropPlanElement(Types.PLAN, plan, true);
    }

    @Override
    public void undoCommand() {
        if (plan == null) {
            return;
        }
        if (!plan.getMasterPlan()) {
            modelManager.storePlanElement(Types.PLAN, plan, true);
        } else {
            modelManager.storePlanElement(Types.MASTERPLAN, plan, true);
        }
    }
}
