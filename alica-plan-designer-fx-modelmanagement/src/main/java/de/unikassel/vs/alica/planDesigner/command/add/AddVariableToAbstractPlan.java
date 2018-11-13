package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;
import de.unikassel.vs.alica.planDesigner.alicamodel.Variable;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;

public class AddVariableToAbstractPlan extends AbstractCommand {

    private final PlanElement planElement;
    private final Variable variable;

    public AddVariableToAbstractPlan(ModelManager manager, PlanElement planElement, Variable variable) {
        super(manager);
        this.planElement = planElement;
        this.variable = variable;
    }

    @Override
    public void doCommand() {
        if (planElement instanceof Plan) {
            ((Plan)planElement).getVariables().add(variable);
        } else {
            ((Behaviour)planElement).getVariables().add(variable);
        }
    }

    @Override
    public void undoCommand() {
        if (planElement instanceof Plan) {
            ((Plan)planElement).getVariables().remove(variable);
        } else {
            ((Behaviour)planElement).getVariables().remove(variable);
        }
    }


}
