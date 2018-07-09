package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PostCondition;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import javafx.geometry.Pos;

public class AddPostConditionToBehaviour extends AbstractCommand {

    protected PostCondition newPostCondition;
    protected PostCondition previousPostCondition;
    protected Behaviour behaviour;

    public AddPostConditionToBehaviour(ModelManager modelManager, PostCondition postCondition, Behaviour behaviour) {
        super(modelManager);
        this.newPostCondition = postCondition;
        this.behaviour = behaviour;
        this.previousPostCondition = null;
    }

    @Override
    public void doCommand() {
        if (behaviour.getPostCondition() != null) {
            this.previousPostCondition = behaviour.getPostCondition();
        }
        behaviour.setPostCondition(newPostCondition);
    }

    @Override
    public void undoCommand() {
        behaviour.setPostCondition(previousPostCondition);
    }
}
