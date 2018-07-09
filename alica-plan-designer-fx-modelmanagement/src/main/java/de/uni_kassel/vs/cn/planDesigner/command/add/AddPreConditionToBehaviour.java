package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PreCondition;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

public class AddPreConditionToBehaviour extends AbstractCommand {

    protected Behaviour behaviour;
    protected PreCondition newPreCondition;
    protected PreCondition previousPreCondition;

    public AddPreConditionToBehaviour(ModelManager modelManager, PreCondition preCondition, Behaviour behaviour) {
        super(modelManager);
        this.behaviour = behaviour;
        this.newPreCondition = preCondition;
    }

    @Override
    public void doCommand() {
        if (behaviour.getPreCondition() != null) {
            previousPreCondition = behaviour.getPreCondition();
        }
        behaviour.setPreCondition(newPreCondition);
    }

    @Override
    public void undoCommand() {
        behaviour.setPreCondition(previousPreCondition);
    }
}
