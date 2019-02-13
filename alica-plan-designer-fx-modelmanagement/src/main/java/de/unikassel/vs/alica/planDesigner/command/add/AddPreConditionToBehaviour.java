package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.alicamodel.PreCondition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

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
        previousPreCondition = behaviour.getPreCondition();
        modelManager.createdPlanElement(Types.PRECONDITION, newPreCondition, behaviour, false);
        behaviour.setPreCondition(newPreCondition);
    }

    @Override
    public void undoCommand() {
        behaviour.setPreCondition(previousPreCondition);
    }
}
