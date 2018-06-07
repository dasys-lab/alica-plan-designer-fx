package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.AnnotatedPlan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanType;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;

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
