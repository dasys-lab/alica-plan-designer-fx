package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.PreCondition;

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
