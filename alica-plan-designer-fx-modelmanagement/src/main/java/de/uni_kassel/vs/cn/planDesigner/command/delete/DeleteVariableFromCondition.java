package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Variable;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

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
