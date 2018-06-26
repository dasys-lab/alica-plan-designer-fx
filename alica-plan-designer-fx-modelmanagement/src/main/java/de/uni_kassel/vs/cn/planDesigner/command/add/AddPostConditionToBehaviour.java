package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PostCondition;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

public class AddPostConditionToBehaviour extends AbstractCommand {

    protected PostCondition postCondition;
    protected Behaviour behaviour;

    public AddPostConditionToBehaviour(ModelManager modelManager, PostCondition postCondition, Behaviour behaviour) {
        super(modelManager);
        this.postCondition = postCondition;
        this.behaviour = behaviour;
    }

    @Override
    public void doCommand() {
        behaviour.setPostCondition(postCondition);
    }

    @Override
    public void undoCommand() {
        behaviour.setPostCondition(null);
    }
}
