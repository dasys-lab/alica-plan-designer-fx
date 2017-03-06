package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.delete;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.Condition;

/**
 * Created by marci on 28.02.17.
 */
public class DeleteConditionFromAbstractPlan extends AbstractCommand<Condition> {
    private final AbstractPlan parentOfElement;

    public DeleteConditionFromAbstractPlan(AbstractPlan parentOfElement, Condition toDelete) {
        super(toDelete);
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        parentOfElement.getConditions().remove(getElementToEdit());
    }

    @Override
    public void undoCommand() {
        parentOfElement.getConditions().add(getElementToEdit());
    }

    @Override
    public String getCommandString() {
        return "Remove Condition " + getElementToEdit() + " from " + parentOfElement.getName();
    }
}
