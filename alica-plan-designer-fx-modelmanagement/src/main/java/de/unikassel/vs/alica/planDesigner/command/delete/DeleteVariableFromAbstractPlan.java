package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.AbstractPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.Variable;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;

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
}
