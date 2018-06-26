package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.RuntimeCondition;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

public class RemoveRuntimeConditionFromBehaviour extends AbstractCommand {

    protected RuntimeCondition condition;
    protected Behaviour behaviour;

    public RemoveRuntimeConditionFromBehaviour(ModelManager modelManager, Behaviour behaviour) {
        super(modelManager);
    }

    @Override
    public void doCommand() {
        condition = behaviour.getRuntimeCondition();
        behaviour.setRuntimeCondition(null);
    }

    @Override
    public void undoCommand() {
        behaviour.setRuntimeCondition(condition);
    }
}
