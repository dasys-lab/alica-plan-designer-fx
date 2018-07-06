package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PreCondition;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

public class RemovePreConditionFromBehaviour extends AbstractCommand {

    private PreCondition previousPreCondition;
    private Behaviour behaviour;
    private long conditionId;

    public RemovePreConditionFromBehaviour(ModelManager manager, Behaviour behaviour, long conditionId) {
        super(manager);
        this.behaviour = behaviour;
        this.conditionId = conditionId;
    }

    @Override
    public void doCommand() {
        for (PreCondition cond : behaviour.getPreConditions()) {
            if (cond.getId() == conditionId) {
                previousPreCondition = cond;
                break;
            }
        }
        behaviour.getPreConditions().remove(previousPreCondition);
    }

    @Override
    public void undoCommand() {
        behaviour.getPreConditions().add(previousPreCondition);
    }
}
