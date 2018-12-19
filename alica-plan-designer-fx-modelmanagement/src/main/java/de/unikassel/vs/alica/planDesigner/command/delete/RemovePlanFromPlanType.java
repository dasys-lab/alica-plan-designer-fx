package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.AnnotatedPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.PlanType;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

import java.util.ArrayList;

public class RemovePlanFromPlanType extends AbstractCommand {

    protected PlanType planType;
    protected AnnotatedPlan annotatedPlan;

    public RemovePlanFromPlanType(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        this.planType = (PlanType) modelManager.getPlanElement(mmq.getParentId());
        this.annotatedPlan = (AnnotatedPlan) modelManager.getPlanElement(mmq.getElementId());
    }

    @Override
    public void doCommand() {
        planType.getPlans().remove(annotatedPlan);
        modelManager.removedPlanElement(Types.ANNOTATEDPLAN, annotatedPlan, planType, false);
    }

    @Override
    public void undoCommand() {
        planType.getPlans().add(annotatedPlan);
        modelManager.createdPlanElement(Types.ANNOTATEDPLAN, annotatedPlan, planType, false);
    }
}
