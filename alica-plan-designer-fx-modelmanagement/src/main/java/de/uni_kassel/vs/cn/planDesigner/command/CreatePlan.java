package de.uni_kassel.vs.cn.planDesigner.command;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.FileSystemUtil;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelModificationQuery;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.Types;

public class CreatePlan extends AbstractCommand {

    protected Plan plan;

    public CreatePlan(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        if (mmq.getModelElementType().equals(Types.PLAN)) {
            this.plan = new Plan();
            this.plan.setName(mmq.getName());
            this.plan.setRelativeDirectory(modelManager.makeRelativePlansDirectory(mmq.getAbsoluteDirectory(), plan.getName()+ "." + FileSystemUtil.PLAN_ENDING));
        }
    }

    @Override
    public void doCommand() {
        modelManager.addPlanElement(plan, Types.PLAN, true);
    }

    @Override
    public void undoCommand() {
        modelManager.removePlanElement(plan, Types.PLAN, true);
    }
}
