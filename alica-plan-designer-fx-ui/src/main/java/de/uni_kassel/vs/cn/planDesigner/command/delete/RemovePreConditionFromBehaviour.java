package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.PreCondition;

/**
 * Created by marci on 16.06.17.
 */
public class RemovePreConditionFromBehaviour extends AbstractCommand<PreCondition> {

    private PreCondition previousPreCondition;

    public RemovePreConditionFromBehaviour(PlanElement affectedPlan) {
        super(null, affectedPlan);
    }

    @Override
    public void doCommand() {
        previousPreCondition = ((Behaviour)getAffectedPlan()).getPreCondition();
        ((Behaviour)getAffectedPlan()).setPreCondition(getElementToEdit());
    }

    @Override
    public void undoCommand() {
        ((Behaviour)getAffectedPlan()).setPreCondition(previousPreCondition);
    }

    @Override
    public String getCommandString() {
        return "Remove Precondition from Behaviour " + getAffectedPlan().getName();
    }
}
