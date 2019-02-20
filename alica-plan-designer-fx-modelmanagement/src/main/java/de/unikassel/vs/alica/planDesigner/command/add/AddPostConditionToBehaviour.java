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
        this.previousPostCondition = behaviour.getPostCondition();
    }

    @Override
    public void doCommand() {
        modelManager.storePlanElement(Types.POSTCONDITION, newPostCondition, behaviour, false);
        behaviour.setPostCondition(newPostCondition);
    }

    @Override
    public void undoCommand() {
        if(previousPostCondition == null){
            modelManager.removedPlanElement(Types.POSTCONDITION, newPostCondition, behaviour, false);
        }else{
            modelManager.storePlanElement(Types.POSTCONDITION, previousPostCondition, behaviour, false);
        }
        behaviour.setPostCondition(previousPostCondition);
    }
}
