package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.alicamodel.PreCondition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class RemovePreConditionFromBehaviour extends AbstractCommand {

    private PreCondition previousPreCondition;
    private Behaviour behaviour;

    public RemovePreConditionFromBehaviour(ModelManager manager, Behaviour behaviour, long conditionId) {
        super(manager);
        this.behaviour = behaviour;
        this.previousPreCondition = (PreCondition) modelManager.getPlanElement(conditionId);
    }

    @Override
    public void doCommand() {
        modelManager.removedPlanElement(Types.PRECONDITION, previousPreCondition, behaviour, false);
        behaviour.setPreCondition(null);
    }

    @Override
    public void undoCommand() {
        if(previousPreCondition != null){
            modelManager.storePlanElement(Types.PRECONDITION, previousPreCondition, behaviour, false);
        }
        behaviour.setPreCondition(previousPreCondition);
    }
}
