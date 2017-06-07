package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.delete;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.*;

/**
 * Created by marci on 28.02.17.
 */
public class DeleteConditionFromPlan extends AbstractCommand<Condition> {
    private final Plan parentOfElement;

    public DeleteConditionFromPlan(Plan parentOfElement, Condition toDelete) {
        super(toDelete, parentOfElement);
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        if (getElementToEdit() instanceof RuntimeCondition) {
            parentOfElement.setRuntimeCondition(null);
        } else if (getElementToEdit() instanceof PreCondition) {
            parentOfElement.setPreCondition(null);
        }
    }

    @Override
    public void undoCommand() {
        if (getElementToEdit() instanceof RuntimeCondition) {
            parentOfElement.setRuntimeCondition((RuntimeCondition) getElementToEdit());
        } else if (getElementToEdit() instanceof PreCondition) {
            parentOfElement.setPreCondition((PreCondition) getElementToEdit());
        }
    }

    @Override
    public String getCommandString() {
        return "Remove Condition " + getElementToEdit() + " from " + parentOfElement.getName();
    }
}
