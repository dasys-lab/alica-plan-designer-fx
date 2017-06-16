package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.PostCondition;

/**
 * Created by marci on 16.06.17.
 */
public class AddPostConditionToBehaviour extends AbstractCommand<PostCondition> {

    public AddPostConditionToBehaviour(PostCondition element, PlanElement affectedPlan) {
        super(element, affectedPlan);
    }

    @Override
    public void doCommand() {
        ((Behaviour) getAffectedPlan()).setPostCondition(getElementToEdit());
    }

    @Override
    public void undoCommand() {
        ((Behaviour) getAffectedPlan()).setPostCondition(null);
    }

    @Override
    public String getCommandString() {
        return "Add Postcondition to Behaviour " +getAffectedPlan().getName();
    }
}
