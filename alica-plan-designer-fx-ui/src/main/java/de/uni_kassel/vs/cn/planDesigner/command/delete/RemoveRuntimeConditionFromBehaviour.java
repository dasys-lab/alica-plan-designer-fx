package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alica.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.RuntimeCondition;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;

/**
 * Created by marci on 16.06.17.
 */
public class RemoveRuntimeConditionFromBehaviour extends AbstractCommand<RuntimeCondition> {

    private RuntimeCondition previousRuntimeCondition;

    public RemoveRuntimeConditionFromBehaviour(PlanElement affectedPlan) {
        super(null, affectedPlan);
    }

    @Override
    public void doCommand() {
        previousRuntimeCondition = ((Behaviour)getAffectedPlan()).getRuntimeCondition();
        ((Behaviour)getAffectedPlan()).setRuntimeCondition(getElementToEdit());
    }

    @Override
    public void undoCommand() {
        ((Behaviour)getAffectedPlan()).setRuntimeCondition(previousRuntimeCondition);
    }

    @Override
    public String getCommandString() {
        return "Remove Precondition from Behaviour " + getAffectedPlan().getName();
    }
}
