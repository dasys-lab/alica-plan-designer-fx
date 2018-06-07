package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

public class DeleteVariableFromAbstractPlan extends AbstractCommand {

    protected Variable variable;
    protected AbstractPlan abstractPlan;

    public DeleteVariableFromAbstractPlan(ModelManager modelManager, Variable variable, AbstractPlan abstractPlan) {
        super(modelManager);
        this.variable = variable;
        this.abstractPlan = abstractPlan;
    }

    @Override
    public void doCommand() {
        if (abstractPlan instanceof Plan) {
            ((Plan) abstractPlan).getVariables().remove(variable);
        } else {
            ((Behaviour) abstractPlan).getVariables().remove(variable);
        }
    }

    @Override
    public void undoCommand() {
        if (abstractPlan instanceof Plan) {
            ((Plan) abstractPlan).getVariables().add(variable);
        } else {
            ((Behaviour) abstractPlan).getVariables().add(variable);
        }
    }

    @Override
    public String getCommandString() {
        return "Remove Variable " + variable.getName() + " from " + abstractPlan.getName();
    }
}
