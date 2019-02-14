package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.alicamodel.PostCondition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class RemovePostConditionFromBehaviour extends AbstractCommand {

    protected PostCondition previousPostCondition;
    protected Behaviour behaviour;

    public RemovePostConditionFromBehaviour(ModelManager modelManager, Behaviour behaviour, long conditionId) {
        super(modelManager);
        this.behaviour = behaviour;
        this.previousPostCondition = (PostCondition) modelManager.getPlanElement(conditionId);
    }

    @Override
    public void doCommand() {
        modelManager.removedPlanElement(Types.POSTCONDITION, previousPostCondition, behaviour, false);
        behaviour.setPostCondition(null);
    }

    @Override
    public void undoCommand() {
        if(previousPostCondition != null){
            modelManager.createdPlanElement(Types.POSTCONDITION, previousPostCondition, behaviour, false);
        }
        behaviour.setPostCondition(previousPostCondition);
    }
}
