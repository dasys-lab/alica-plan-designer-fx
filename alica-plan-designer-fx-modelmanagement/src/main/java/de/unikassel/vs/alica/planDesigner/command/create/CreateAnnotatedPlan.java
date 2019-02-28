package de.unikassel.vs.alica.planDesigner.command.create;

import de.unikassel.vs.alica.planDesigner.alicamodel.AnnotatedPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.PlanType;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class CreateAnnotatedPlan extends Command {

    private PlanType planType;
    private AnnotatedPlan annotatedPlan;

    public CreateAnnotatedPlan(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.planType = (PlanType) modelManager.getPlanElement(mmq.getParentId());
        this.annotatedPlan = createAnnotatedPlan();
        if(this.modelManager.checkForInclusionLoop(planType, Types.PLANTYPE, annotatedPlan, Types.ANNOTATEDPLAN)){
            throw new RuntimeException(
                    String.format("Plan \"%s\" can not be added to PlanType \"%s\" because of loop in model"
                            , annotatedPlan.getPlan().getName(), planType.getName()));
        }
    }

    public AnnotatedPlan createAnnotatedPlan() {
        AnnotatedPlan annotatedPlan = new AnnotatedPlan();
        annotatedPlan.setPlan((Plan) modelManager.getPlanElement(mmq.getElementId()));
        annotatedPlan.setActivated(true);
        return annotatedPlan;
    }

    @Override
    public void doCommand() {
        this.planType.addPlan(annotatedPlan);
        this.modelManager.storePlanElement(Types.ANNOTATEDPLAN, annotatedPlan, false);
        this.fireEvent(ModelEventType.ELEMENT_CREATED, annotatedPlan);
    }

    @Override
    public void undoCommand() {
        planType.removePlan(annotatedPlan);
        modelManager.dropPlanElement(Types.ANNOTATEDPLAN, annotatedPlan, false);
        this.fireEvent(ModelEventType.ELEMENT_DELETED, annotatedPlan);
    }
}