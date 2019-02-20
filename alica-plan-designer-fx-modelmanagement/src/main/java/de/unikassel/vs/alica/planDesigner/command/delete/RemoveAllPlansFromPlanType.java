package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.AnnotatedPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.PlanType;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

import java.util.ArrayList;
import java.util.List;

public class RemoveAllPlansFromPlanType extends AbstractCommand {

    private List<AnnotatedPlan> backupPlans;
    private PlanType planType;

    public RemoveAllPlansFromPlanType(ModelManager manager, ModelModificationQuery mmq) {
        super(manager);
        this.planType = (PlanType) modelManager.getPlanElement(mmq.getElementId());
        backupPlans = new ArrayList<>(planType.getPlans());
    }

    @Override
    public void doCommand() {
        for(AnnotatedPlan annotatedPlan : backupPlans) {
            planType.removePlan(annotatedPlan);
            modelManager.removedPlanElement(Types.ANNOTATEDPLAN, annotatedPlan, planType, false);
        }
    }

    @Override
    public void undoCommand() {
        for(AnnotatedPlan annotatedPlan : backupPlans) {
            planType.addPlan(annotatedPlan);
            modelManager.storePlanElement(Types.ANNOTATEDPLAN, annotatedPlan, planType, false);
        }
    }

}
