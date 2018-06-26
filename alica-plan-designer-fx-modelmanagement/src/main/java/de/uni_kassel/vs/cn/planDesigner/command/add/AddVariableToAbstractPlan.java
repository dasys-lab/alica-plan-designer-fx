package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Variable;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

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
