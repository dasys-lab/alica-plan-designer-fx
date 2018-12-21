package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.AnnotatedPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.PlanType;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class AddPlanToPlanType extends AbstractCommand {

    private PlanType planType;
    private AnnotatedPlan annotatedPlan;

    public AddPlanToPlanType(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);

        this.planType = (PlanType) modelManager.getPlanElement(mmq.getParentId());

        this.annotatedPlan = new AnnotatedPlan();
        this.annotatedPlan.setPlan((Plan) modelManager.getPlanElement(mmq.getElementId()));
        this.annotatedPlan.setActivated(true);
    }

    @Override
    public void doCommand() {
        planType.addPlan(annotatedPlan);
        modelManager.createdPlanElement(Types.ANNOTATEDPLAN, annotatedPlan, planType, false);
    }

    @Override
    public void undoCommand() {
        planType.removePlan(annotatedPlan);
        modelManager.removedPlanElement(Types.ANNOTATEDPLAN, annotatedPlan, planType, false);
    }
}
