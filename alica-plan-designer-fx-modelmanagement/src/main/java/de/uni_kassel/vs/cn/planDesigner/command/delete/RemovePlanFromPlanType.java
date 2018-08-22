package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.AnnotatedPlan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanType;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.Types;

import java.util.ArrayList;

public class RemovePlanFromPlanType extends AbstractCommand {

    protected PlanType planType;
    protected Plan plan;
    protected AnnotatedPlan annotatedPlan;

    public RemovePlanFromPlanType(ModelManager modelManager, Plan plan, PlanType planType) {
        super(modelManager);
        this.plan = plan;
        this.planType = planType;
        this.annotatedPlan = new AnnotatedPlan();
        annotatedPlan.setPlan(plan);
    }

    @Override
    public void doCommand() {
        ArrayList<AnnotatedPlan> plans = planType.getPlans();
        for(int i = 0; i < plans.size(); i++) {
            if(plans.get(i).getPlan().getId() == plan.getId()) {
                plans.remove(i);
                break;
            }
        }
        modelManager.removePlanElement(annotatedPlan, Types.ANNOTATEDPLAN, false);
    }

    @Override
    public void undoCommand() {
        planType.getPlans().add(annotatedPlan);
        modelManager.addPlanElement(Types.ANNOTATEDPLAN, annotatedPlan, null, false);
    }
}
