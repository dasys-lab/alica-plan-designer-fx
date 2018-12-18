package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.AnnotatedPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.PlanType;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class AddPlanToPlanType extends AbstractCommand {

    private PlanType parentOfElement;
    private AnnotatedPlan annotatedPlan;

    public AddPlanToPlanType(ModelManager modelManager, AnnotatedPlan annotatedPlan, PlanType parentOfElement) {
        super(modelManager);
        this.parentOfElement = parentOfElement;
        this.annotatedPlan = annotatedPlan;
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
