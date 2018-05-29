package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Variable;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;

/**
 * Created by marci on 26.02.17.
 */
public class DeleteVariableFromCondition extends AbstractCommand<Variable> {

    private final Condition parentOfElement;

    public DeleteVariableFromCondition(Variable toDelete, Condition parentOfElement, Plan affectedPlan) {
        super(toDelete, affectedPlan);
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        parentOfElement.getVars().remove(getElementToEdit());
    }

    @Override
    public void undoCommand() {
        parentOfElement.getVars().add(getElementToEdit());
    }

    @Override
    public String getCommandString() {
        return "Remove Variable " + getElementToEdit().getName() + " from " + parentOfElement.getName();
    }
}
