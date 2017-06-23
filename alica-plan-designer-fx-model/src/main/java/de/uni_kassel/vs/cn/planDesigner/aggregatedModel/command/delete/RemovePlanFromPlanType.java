package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.delete;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.AnnotatedPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanType;

/**
 * Created by marci on 17.03.17.
 */
public class RemovePlanFromPlanType extends AbstractCommand<AnnotatedPlan> {

    private PlanType parentOfElement;

    public RemovePlanFromPlanType(AnnotatedPlan element, PlanType parentOfElement) {
        super(element, parentOfElement);
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlans().remove(getElementToEdit());
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlans().add(getElementToEdit());
    }

    @Override
    public String getCommandString() {
        return "Remove plan " + getElementToEdit().getName() + " from plantype " + parentOfElement.getName();
    }
}
