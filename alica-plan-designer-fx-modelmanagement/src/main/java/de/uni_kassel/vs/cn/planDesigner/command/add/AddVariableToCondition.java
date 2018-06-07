package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Variable;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

public class AddVariableToCondition extends AbstractCommand {

    protected Condition condition;
    protected Variable variable;

    public AddVariableToCondition(ModelManager modelManager, Condition condition) {
        super(modelManager);
        this.variable = new Variable();
        this.condition = condition;
    }

    @Override
    public void doCommand() {
        condition.getVariables().add(variable);
    }

    @Override
    public void undoCommand() {
        condition.getVariables().remove(variable);
    }

    @Override
    public String getCommandString() {
        return "Add new variable " + variable.getName() + " to " + condition.getName();
    }


}
