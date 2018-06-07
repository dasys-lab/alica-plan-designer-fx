package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.RuntimeCondition;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;

public class AddRuntimeConditionToBehaviour extends AbstractCommand {

    public AddRuntimeConditionToBehaviour(RuntimeCondition element, PlanElement affectedPlan) {
        super(element, affectedPlan);
    }

    @Override
    public void doCommand() {
        ((Behaviour) getAffectedPlan()).setRuntimeCondition((RuntimeCondition) getElementToEdit());
    }

    @Override
    public void undoCommand() {
        ((Behaviour) getAffectedPlan()).setRuntimeCondition((RuntimeCondition) getElementToEdit());
    }

    @Override
    public String getCommandString() {
        return "Add Runtimecondition to Behaviour " + getAffectedPlan().getName();
    }
}
