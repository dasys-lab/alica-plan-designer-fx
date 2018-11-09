package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.AnnotatedPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.PlanType;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

import java.util.ArrayList;
import java.util.List;

public class RemoveAllPlansFromPlanType extends AbstractCommand {

    private List<AnnotatedPlan> backupPlans;
    private PlanType planType;

    public RemoveAllPlansFromPlanType(ModelManager manager, PlanType planType) {
        super(manager);
        this.planType = planType;
        backupPlans = new ArrayList<>();
    }

    @Override
    public void doCommand() {
        backupPlans.addAll(planType.getPlans());
        for(AnnotatedPlan annotatedPlan : backupPlans) {
            modelManager.removePlanElement(Types.ANNOTATEDPLAN, annotatedPlan, planType, false);
        }
        planType.getPlans().clear();

    }

    @Override
    public void undoCommand() {
        planType.getPlans().addAll(backupPlans);
        for(AnnotatedPlan annotatedPlan : backupPlans) {
            modelManager.addPlanElement(Types.ANNOTATEDPLAN, annotatedPlan, planType, false);
        }
    }

}
