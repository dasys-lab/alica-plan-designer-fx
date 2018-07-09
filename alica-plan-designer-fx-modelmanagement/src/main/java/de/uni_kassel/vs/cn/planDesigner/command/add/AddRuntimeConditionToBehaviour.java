package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.RuntimeCondition;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

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
