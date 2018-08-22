package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanType;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelModificationQuery;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.Types;

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
        modelManager.removePlanElement(planType, Types.PLANTYPE, true);
    }

    @Override
    public void undoCommand() {
        if (planType == null) {
            return;
        }
        modelManager.addPlanElement(Types.PLANTYPE, planType, null, true);
    }
}
