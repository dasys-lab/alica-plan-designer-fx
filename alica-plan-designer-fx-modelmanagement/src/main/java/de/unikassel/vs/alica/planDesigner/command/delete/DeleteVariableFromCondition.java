package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Condition;
import de.unikassel.vs.alica.planDesigner.alicamodel.Variable;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;

public class DeleteVariableFromCondition extends AbstractCommand {

    protected Condition condition;
    protected Variable variable;

    public DeleteVariableFromCondition(ModelManager modelManager, Variable variable, Condition condition) {
        super(modelManager);
        this.variable = variable;
        this.condition = condition;
    }

    @Override
    public void doCommand() {
        condition.getVariables().remove(variable);
    }

    @Override
    public void undoCommand() {
        condition.getVariables().add(variable);
    }
}
