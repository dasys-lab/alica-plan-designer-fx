package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.Condition;
import de.unikassel.vs.alica.planDesigner.alicamodel.Variable;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;

public class AddVariableToCondition extends AbstractCommand {

    protected Condition condition;
    protected Variable variable;

    public AddVariableToCondition(ModelManager modelManager, Condition condition) {
        super(modelManager);
        this.condition = condition;
        // TODO change constructor to take mmq and init variable with the parameters of the mmq
    }

    @Override
    public void doCommand() {
        condition.getVariables().add(variable);
    }

    @Override
    public void undoCommand() {
        condition.getVariables().remove(variable);
    }
}
