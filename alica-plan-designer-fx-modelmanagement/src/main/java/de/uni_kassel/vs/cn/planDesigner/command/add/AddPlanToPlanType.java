package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.AnnotatedPlan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanType;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.Types;

public class AddPlanToPlanType extends AbstractCommand {

    private PlanType parentOfElement;
    private Plan plan;
    private AnnotatedPlan annotatedPlan;

    public AddPlanToPlanType(ModelManager modelManager, Plan plan, PlanType parentOfElement) {
        super(modelManager);
        this.plan = plan;
        this.parentOfElement = parentOfElement;
        this.annotatedPlan = new AnnotatedPlan();
        annotatedPlan.setPlan(plan);
        annotatedPlan.setActivated(true);
    }

    @Override
    public void doCommand() {
        modelManager.addPlanElement(Types.ANNOTATEDPLAN, annotatedPlan, parentOfElement, false);
        parentOfElement.getPlans().add(annotatedPlan);
    }

    @Override
    public void undoCommand() {
        modelManager.removePlanElement(Types.ANNOTATEDPLAN, annotatedPlan, parentOfElement, false);
        parentOfElement.getPlans().remove(annotatedPlan);
    }
}
