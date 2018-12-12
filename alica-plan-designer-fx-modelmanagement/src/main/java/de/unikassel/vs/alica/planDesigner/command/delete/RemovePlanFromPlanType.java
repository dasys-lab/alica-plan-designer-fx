package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.AnnotatedPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.PlanType;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

import java.util.ArrayList;

public class RemovePlanFromPlanType extends AbstractCommand {

    protected PlanType planType;
    protected Plan plan;
    protected AnnotatedPlan annotatedPlan;

    public RemovePlanFromPlanType(ModelManager modelManager, Plan plan, PlanType planType) {
        super(modelManager);
        this.plan = plan;
        this.planType = planType;
        // TODO: Don't create your own AnnotatedPlan, use the existing one!
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
        modelManager.removePlanElement(Types.ANNOTATEDPLAN, annotatedPlan, planType, false);
    }

    @Override
    public void undoCommand() {
        planType.getPlans().add(annotatedPlan);
        modelManager.addPlanElement(Types.ANNOTATEDPLAN, annotatedPlan, planType, false);
    }
}
