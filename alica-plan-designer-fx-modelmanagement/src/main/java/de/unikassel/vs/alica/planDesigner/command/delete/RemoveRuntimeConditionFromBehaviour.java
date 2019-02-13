package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.alicamodel.RuntimeCondition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

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
            modelManager.removedPlanElement(Types.RUNTIMECONDITION, previousRuntimeCondition, behaviour, false);
        }
        behaviour.setRuntimeCondition(null);
    }

    @Override
    public void undoCommand() {
        behaviour.setRuntimeCondition(previousRuntimeCondition);
    }
}
