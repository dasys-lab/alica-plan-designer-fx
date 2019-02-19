package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.alicamodel.RuntimeCondition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class RemoveRuntimeConditionFromBehaviour extends AbstractCommand {

    protected RuntimeCondition previousRuntimeCondition;
    protected Behaviour behaviour;

    public RemoveRuntimeConditionFromBehaviour(ModelManager modelManager, Behaviour behaviour, long conditionId) {
        super(modelManager);
        this.behaviour = behaviour;
        this.previousRuntimeCondition = (RuntimeCondition) modelManager.getPlanElement(conditionId);
    }

    @Override
    public void doCommand() {
        modelManager.removedPlanElement(Types.RUNTIMECONDITION, previousRuntimeCondition, behaviour, false);
        behaviour.setRuntimeCondition(null);
    }

    @Override
    public void undoCommand() {
        if(previousRuntimeCondition != null){
            modelManager.createdPlanElement(Types.RUNTIMECONDITION, previousRuntimeCondition, behaviour, false);
        }
        behaviour.setRuntimeCondition(previousRuntimeCondition);
    }
}
