package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.AnnotatedPlan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanType;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

public class AddPlanToPlanType extends AbstractCommand {

    private PlanType parentOfElement;

    private AnnotatedPlan plan;

    public AddPlanToPlanType(ModelManager modelManager, AnnotatedPlan plan, PlanType parentOfElement) {
        super(modelManager);
        this.plan = plan;
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlans().add(plan);
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlans().remove(plan);
    }
}
