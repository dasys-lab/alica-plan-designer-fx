package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.generator.AlicaModelUtils;
import de.uni_kassel.vs.cn.planDesigner.alica.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;

/**
 * Created by marci on 02.12.16.
 */
public class AddAbstractPlanToState extends AbstractCommand<AbstractPlan> {
    private State parentOfElement;

    public AddAbstractPlanToState(AbstractPlan element, State parentOfElement) {
        super(element, parentOfElement.getInPlan());
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlans().add(getElementToEdit());
        AlicaModelUtils.addParametrisations(getElementToEdit(), parentOfElement);
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlans().remove(getElementToEdit());
        parentOfElement
                .getParametrisation()
                .removeIf(param -> param.getSubplan().getId() == getElementToEdit().getId());
    }

    @Override
    public String getCommandString() {
        return "Add Element " + getElementToEdit().getName() + " in State " + parentOfElement.getName();
    }
}
