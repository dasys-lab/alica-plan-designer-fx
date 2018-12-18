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
    protected AnnotatedPlan annotatedPlan;

    public RemovePlanFromPlanType(ModelManager modelManager, AnnotatedPlan annotatedPlan, PlanType planType) {
        super(modelManager);
        this.planType = planType;
        this.annotatedPlan = annotatedPlan;
    }

    @Override
    public void doCommand() {
        ArrayList<AnnotatedPlan> plans = planType.getPlans();
        for(int i = 0; i < plans.size(); i++) {
            if(plans.get(i).getId() == annotatedPlan.getId()) {
                plans.remove(i);
                break;
            }
        }
        modelManager.removePlanElement(Types.ANNOTATEDPLAN, annotatedPlan, planType, false);
        planType.getPlans().remove(annotatedPlan);
    }

    @Override
    public void undoCommand() {
        planType.getPlans().add(annotatedPlan);
        modelManager.addPlanElement(Types.ANNOTATEDPLAN, annotatedPlan, planType, false);
    }
}
