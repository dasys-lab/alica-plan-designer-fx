package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.delete;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alica.PostCondition;

/**
 * Created by marci on 16.06.17.
 */
public class RemovePostConditionFromBehaviour extends AbstractCommand<PostCondition> {

    private PostCondition previousPostCondition;

    public RemovePostConditionFromBehaviour(Behaviour affectedPlan) {
        super(null, affectedPlan);
    }

    @Override
    public void doCommand() {
        previousPostCondition = ((Behaviour)getAffectedPlan()).getPostCondition();
        ((Behaviour)getAffectedPlan()).setPostCondition(getElementToEdit());
    }

    @Override
    public void undoCommand() {
        ((Behaviour)getAffectedPlan()).setPostCondition(previousPostCondition);
    }

    @Override
    public String getCommandString() {
        return "Remove Postcondtion from Behaviour " + getAffectedPlan().getName();
    }
}
