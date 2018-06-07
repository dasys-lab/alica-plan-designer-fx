package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Variable;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;

public class DeleteVariableFromCondition extends AbstractCommand {

    private final Condition parentOfElement;

    public DeleteVariableFromCondition(Variable toDelete, Condition parentOfElement, Plan affectedPlan) {
        super(toDelete, affectedPlan);
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        parentOfElement.getVariables().remove(getElementToEdit());
    }

    @Override
    public void undoCommand() {
        parentOfElement.getVariables().add((Variable)getElementToEdit());
    }

    @Override
    public String getCommandString() {
        return "Remove Variable " + getElementToEdit().getName() + " from " + parentOfElement.getName();
    }
}
