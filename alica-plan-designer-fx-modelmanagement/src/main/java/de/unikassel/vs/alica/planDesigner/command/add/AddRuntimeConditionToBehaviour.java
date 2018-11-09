package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.alicamodel.RuntimeCondition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;

public class AddRuntimeConditionToBehaviour extends AbstractCommand {

    protected RuntimeCondition previousRuntimeCondition;
    protected RuntimeCondition newRuntimeCondition;
    protected Behaviour behaviour;

    public AddRuntimeConditionToBehaviour(ModelManager modelManager, RuntimeCondition runtimeCondition, Behaviour behaviour) {
        super(modelManager);
        this.newRuntimeCondition = runtimeCondition;
        this.behaviour = behaviour;
        this.previousRuntimeCondition = null;
    }

    @Override
    public void doCommand() {
        previousRuntimeCondition = behaviour.getRuntimeCondition();
        behaviour.setRuntimeCondition(newRuntimeCondition);

    }

    @Override
    public void undoCommand() {
        behaviour.setRuntimeCondition(previousRuntimeCondition);
    }
}
