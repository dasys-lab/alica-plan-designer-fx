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
        this.previousPreCondition = null;
    }

    @Override
    public void doCommand() {
        if (behaviour.getPreCondition().getId() == conditionId) {
            previousPreCondition = behaviour.getPreCondition();
            behaviour.setPreCondition(null);
        }

    }

    @Override
    public void undoCommand() {
        behaviour.setPreCondition(previousPreCondition);
    }
}
