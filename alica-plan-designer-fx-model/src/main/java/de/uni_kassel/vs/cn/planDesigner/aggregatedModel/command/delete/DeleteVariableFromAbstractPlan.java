package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.delete;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.Variable;

/**
 * Created by marci on 26.02.17.
 */
public class DeleteVariableFromAbstractPlan extends AbstractCommand<Variable> {

    private final AbstractPlan parentOfElement;

    public DeleteVariableFromAbstractPlan(Variable toDelete, AbstractPlan parentOfElement) {
        super(toDelete);
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
