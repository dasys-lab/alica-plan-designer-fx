package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PreCondition;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

public class AddPreConditionToBehaviour extends AbstractCommand {

    protected Behaviour behaviour;
    protected PreCondition preCondition;

    public AddPreConditionToBehaviour(ModelManager modelManager, PreCondition preCondition, Behaviour behaviour) {
        super(modelManager);
        this.behaviour = behaviour;
        this.preCondition = preCondition;
    }

    @Override
    public void doCommand() {
        behaviour.setPreCondition(preCondition);
    }

    @Override
    public void undoCommand() {
        behaviour.setPreCondition(null);
    }

    @Override
    public String getCommandString() {
        return "Add Precondition to Behaviour " + behaviour.getName();
    }
}
