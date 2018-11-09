package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.alicamodel.PostCondition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;

public class RemovePostConditionFromBehaviour extends AbstractCommand {

    protected PostCondition previousPostCondition;
    protected Behaviour behaviour;
    private long conditionId;

    public RemovePostConditionFromBehaviour(ModelManager modelManager, Behaviour behaviour, long conditionId) {
        super(modelManager);
        this.behaviour = behaviour;
        this.conditionId = conditionId;
        this.previousPostCondition = null;
    }

    @Override
    public void doCommand() {
        if (behaviour.getPostCondition().getId() == conditionId) {
            previousPostCondition = behaviour.getPostCondition();
            behaviour.setPostCondition(null);
        }
    }

    @Override
    public void undoCommand() {
        behaviour.setPostCondition(previousPostCondition);
    }
}
