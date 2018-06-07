package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PostCondition;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;

public class AddPostConditionToBehaviour extends AbstractCommand {

    public AddPostConditionToBehaviour(PostCondition element, PlanElement affectedPlan) {
        super(element, affectedPlan);
    }

    @Override
    public void doCommand() {
        ((Behaviour) getAffectedPlan()).setPostCondition((PostCondition) getElementToEdit());
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
