package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.Condition;

/**
 * Created by marci on 02.12.16.
 */
public class AddConditionToPlan extends AbstractCommand<Condition> {

    private AbstractPlan parentOfElement;

    public AddConditionToPlan(AbstractPlan parentOfElement, Condition element) {
        super(element);
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        parentOfElement.getConditions().add(getElementToEdit());
    }

    @Override
    public void undoCommand() {
        parentOfElement.getConditions().remove(getElementToEdit());
    }

    @Override
    public String getCommandString() {
        return "Add new condition to " + parentOfElement.getName();
    }
}
