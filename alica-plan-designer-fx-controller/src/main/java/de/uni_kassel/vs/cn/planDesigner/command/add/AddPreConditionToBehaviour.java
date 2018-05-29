package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PreCondition;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;

/**
 * Created by marci on 16.06.17.
 */
public class AddPreConditionToBehaviour extends AbstractCommand<PreCondition> {

    public AddPreConditionToBehaviour(PreCondition element, PlanElement affectedPlan) {
        super(element, affectedPlan);
    }

    @Override
    public void doCommand() {
        ((Behaviour) getAffectedPlan()).setPreCondition(getElementToEdit());
    }

    @Override
    public void undoCommand() {
        ((Behaviour) getAffectedPlan()).setPreCondition(null);
    }

    @Override
    public String getCommandString() {
        return "Add Precondition to Behaviour " + getAffectedPlan().getName();
    }
}
