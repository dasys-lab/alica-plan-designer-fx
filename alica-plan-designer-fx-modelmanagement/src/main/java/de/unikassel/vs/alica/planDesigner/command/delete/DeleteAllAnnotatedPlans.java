package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.AnnotatedPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.PlanType;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

import java.util.ArrayList;
import java.util.List;

public class DeleteAllAnnotatedPlans extends Command {

    private List<AnnotatedPlan> backupPlans;
    private PlanType planType;

    public DeleteAllAnnotatedPlans(ModelManager manager, ModelModificationQuery mmq) {
        super(manager, mmq);
        this.planType = (PlanType) modelManager.getPlanElement(mmq.getElementId());
        this.backupPlans = new ArrayList<>(planType.getPlans());
    }

    @Override
    public void doCommand() {
        for(AnnotatedPlan annotatedPlan : backupPlans) {
            planType.removePlan(annotatedPlan);
            modelManager.dropPlanElement(Types.ANNOTATEDPLAN, annotatedPlan, false);
            this.fireEvent(ModelEventType.ELEMENT_DELETED, annotatedPlan);
        }
    }

    @Override
    public void undoCommand() {
        for(AnnotatedPlan annotatedPlan : backupPlans) {
            planType.addPlan(annotatedPlan);
            modelManager.storePlanElement(Types.ANNOTATEDPLAN, annotatedPlan, false);
            this.fireEvent(ModelEventType.ELEMENT_CREATED, annotatedPlan);
        }
    }

}
