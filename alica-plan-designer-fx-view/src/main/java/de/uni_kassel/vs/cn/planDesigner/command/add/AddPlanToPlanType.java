package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.AnnotatedPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanType;

import static de.uni_kassel.vs.cn.generator.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 17.03.17.
 */
public class AddPlanToPlanType extends AbstractCommand<Plan> {

    private PlanType parentOfElement;

    private AnnotatedPlan copyForRemoval;

    public AddPlanToPlanType(Plan element, PlanType parentOfElement) {
        super(element, parentOfElement);
        this.parentOfElement = parentOfElement;
    }

    public AnnotatedPlan getCopyForRemoval() {
        return copyForRemoval;
    }

    @Override
    public void doCommand() {
        if (copyForRemoval == null) {
            copyForRemoval = getAlicaFactory().createAnnotatedPlan();
            copyForRemoval.setActivated(true);
            copyForRemoval.setPlan(getElementToEdit());
        }
        parentOfElement.getPlans().add(copyForRemoval);
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlans().remove(copyForRemoval);
    }

    @Override
    public String getCommandString() {
        return "Add Plan " + getElementToEdit().getName() + "to Plantype " + parentOfElement.getName();
    }
}
