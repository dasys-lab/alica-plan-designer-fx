package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

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
        modelManager.removePlanElement(Types.PLAN, plan, null, true);
    }

    @Override
    public void undoCommand() {
        if (plan == null) {
            return;
        }
        if (!plan.getMasterPlan()) {
            modelManager.addPlanElement(Types.PLAN, plan, null, true);
        } else {
            modelManager.addPlanElement(Types.MASTERPLAN, plan, null, true);
        }
    }
}
