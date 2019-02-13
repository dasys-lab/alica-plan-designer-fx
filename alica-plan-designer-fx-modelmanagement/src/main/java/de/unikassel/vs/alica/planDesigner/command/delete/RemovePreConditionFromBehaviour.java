package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.alicamodel.PreCondition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

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
            modelManager.removedPlanElement(Types.PRECONDITION, previousPreCondition, behaviour, false);
            behaviour.setPreCondition(null);
        }

    }

    @Override
    public void undoCommand() {
        behaviour.setPreCondition(previousPreCondition);
    }
}
