package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.State;

/**
 * Created by marci on 02.12.16.
 */
public class AddAbstractPlanToState extends AbstractCommand<AbstractPlan> {
    private State parentOfElement;

    public AddAbstractPlanToState(AbstractPlan element, State parentOfElement) {
        super(element);
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlans().add(getElementToEdit());
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlans().remove(getElementToEdit());
    }

    @Override
    public String getCommandString() {
        return "Add Element " + getElementToEdit().getName() + " in State " + parentOfElement.getName();
    }
}
