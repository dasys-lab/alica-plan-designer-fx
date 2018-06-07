package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanType;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

public class RemovePlanFromPlanType extends AbstractCommand {

    protected PlanType planType;
    protected Plan plan;

    public RemovePlanFromPlanType(ModelManager modelManager, Plan plan, PlanType planType) {
        super(modelManager);
        this.plan = plan;
        this.planType = planType;
    }

    @Override
    public void doCommand() {
        planType.getPlans().remove(plan);
    }

    @Override
    public void undoCommand() {
        planType.getPlans().add(plan);
    }

    @Override
    public String getCommandString() {
        return "Remove plan " + plan.getName() + " from plantype " + planType.getName();
    }
}
