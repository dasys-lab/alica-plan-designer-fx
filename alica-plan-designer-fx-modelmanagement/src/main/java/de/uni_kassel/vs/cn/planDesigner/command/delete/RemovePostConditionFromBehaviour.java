package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PostCondition;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

public class RemovePostConditionFromBehaviour extends AbstractCommand {

    protected PostCondition previousPostCondition;
    protected Behaviour behaviour;
    private long conditionId;

    public RemovePostConditionFromBehaviour(ModelManager modelManager, Behaviour behaviour, long conditionId) {
        super(modelManager);
        this.behaviour = behaviour;
        this.conditionId = conditionId;
    }

    @Override
    public void doCommand() {
        for (PostCondition cond : behaviour.getPostConditions()) {
            if (cond.getId() == conditionId) {
                previousPostCondition = cond;
                break;
            }
        }
        behaviour.getPreConditions().remove(previousPostCondition);
    }

    @Override
    public void undoCommand() {
        (behaviour).getPostConditions().add(previousPostCondition);
    }
}
