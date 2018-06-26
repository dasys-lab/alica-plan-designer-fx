package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PostCondition;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

public class RemovePostConditionFromBehaviour extends AbstractCommand {

    protected PostCondition postCondition;
    protected Behaviour behaviour;

    public RemovePostConditionFromBehaviour(ModelManager modelManager, Behaviour behaviour) {
        super(modelManager);
    }

    @Override
    public void doCommand() {
        postCondition = behaviour.getPostCondition();
        behaviour.setPostCondition(null);
    }

    @Override
    public void undoCommand() {
        (behaviour).setPostCondition(postCondition);
    }
}
