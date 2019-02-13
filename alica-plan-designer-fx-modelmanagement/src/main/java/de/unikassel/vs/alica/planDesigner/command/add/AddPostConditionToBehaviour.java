package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.alicamodel.PostCondition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

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
        this.previousPostCondition = behaviour.getPostCondition();
        modelManager.createdPlanElement(Types.POSTCONDITION, newPostCondition, behaviour, false);
        behaviour.setPostCondition(newPostCondition);
    }

    @Override
    public void undoCommand() {
        behaviour.setPostCondition(previousPostCondition);
    }
}
