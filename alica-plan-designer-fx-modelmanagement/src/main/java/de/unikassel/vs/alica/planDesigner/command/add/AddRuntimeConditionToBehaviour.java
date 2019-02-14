package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.alicamodel.RuntimeCondition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class AddRuntimeConditionToBehaviour extends AbstractCommand {

    protected RuntimeCondition previousRuntimeCondition;
    protected RuntimeCondition newRuntimeCondition;
    protected Behaviour behaviour;

    public AddRuntimeConditionToBehaviour(ModelManager modelManager, RuntimeCondition runtimeCondition, Behaviour behaviour) {
        super(modelManager);
        this.newRuntimeCondition = runtimeCondition;
        this.behaviour = behaviour;
        this.previousRuntimeCondition = behaviour.getRuntimeCondition();
    }

    @Override
    public void doCommand() {
        modelManager.createdPlanElement(Types.RUNTIMECONDITION, newRuntimeCondition, behaviour, false);
        behaviour.setRuntimeCondition(newRuntimeCondition);
    }

    @Override
    public void undoCommand() {
        if(previousRuntimeCondition == null){
            modelManager.removedPlanElement(Types.RUNTIMECONDITION, newRuntimeCondition, behaviour, false);
        }else{
            modelManager.createdPlanElement(Types.RUNTIMECONDITION, previousRuntimeCondition, behaviour, false);
        }
        behaviour.setRuntimeCondition(previousRuntimeCondition);
    }
}
