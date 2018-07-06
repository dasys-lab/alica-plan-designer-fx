package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.RuntimeCondition;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

public class AddRuntimeConditionToBehaviour extends AbstractCommand {

    protected RuntimeCondition runtimeCondition;
    protected Behaviour behaviour;

    public AddRuntimeConditionToBehaviour(ModelManager modelManager, RuntimeCondition runtimeCondition, Behaviour behaviour) {
        super(modelManager);
        this.runtimeCondition = runtimeCondition;
        this.behaviour = behaviour;
    }

    @Override
    public void doCommand() {
        behaviour.getRuntimeConditions().add(runtimeCondition);
    }

    @Override
    public void undoCommand() {
        behaviour.getRuntimeConditions().remove(runtimeCondition);
    }
}
