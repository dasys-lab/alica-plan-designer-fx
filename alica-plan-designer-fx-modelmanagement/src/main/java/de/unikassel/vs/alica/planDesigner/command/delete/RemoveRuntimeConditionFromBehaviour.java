package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.alicamodel.RuntimeCondition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;

public class RemoveRuntimeConditionFromBehaviour extends AbstractCommand {

    protected RuntimeCondition previousRuntimeCondition;
    protected Behaviour behaviour;
    protected long conditionId;

    public RemoveRuntimeConditionFromBehaviour(ModelManager modelManager, Behaviour behaviour, long conditionId) {
        super(modelManager);
        this.behaviour = behaviour;
        this.conditionId = conditionId;
        this.previousRuntimeCondition = null;
    }

    @Override
    public void doCommand() {
        if (behaviour.getRuntimeCondition().getId() == conditionId) {
            previousRuntimeCondition = behaviour.getRuntimeCondition();
        }
        behaviour.setRuntimeCondition(null);
    }

    @Override
    public void undoCommand() {
        behaviour.setRuntimeCondition(previousRuntimeCondition);
    }
}
