package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.PlanType;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class DeletePlanType extends AbstractCommand {

    protected PlanType planType;

    public DeletePlanType(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        planType = (PlanType) modelManager.getPlanElement(mmq.getElementId());
    }

    @Override
    public void doCommand() {
        if (planType == null) {
            return;
        }
        modelManager.removedPlanElement(Types.PLANTYPE, planType, null, true);
    }

    @Override
    public void undoCommand() {
        if (planType == null) {
            return;
        }
        modelManager.storePlanElement(Types.PLANTYPE, planType, null, true);
    }
}
