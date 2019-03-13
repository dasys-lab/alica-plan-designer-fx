package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.AbstractPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;

public class AddAbstractPlan extends Command {
    protected State state;
    protected AbstractPlan abstractPlan;

    public AddAbstractPlan(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.state = (State) modelManager.getPlanElement(mmq.getParentId());
        this.abstractPlan = (AbstractPlan) modelManager.getPlanElement(mmq.getElementId());

        if(modelManager.checkForInclusionLoop(state, abstractPlan)){
            throw new RuntimeException(
                    String.format("AbstractPlan \"%s\" can not be added to State \"%s\" because of loop in model",
                    abstractPlan.getName(), state.getName())
            );
        }
    }

    @Override
    public void doCommand() {
        //Don't put AbstractPlan, if the same existing
        if (state.getPlans().contains(abstractPlan)) {
            return;
        }

        this.state.addAbstractPlan(abstractPlan);
        this.fireEvent(ModelEventType.ELEMENT_ADDED, abstractPlan);
    }

    @Override
    public void undoCommand() {
        this.state.removeAbstractPlan(abstractPlan);
        this.fireEvent(ModelEventType.ELEMENT_REMOVED, abstractPlan);
    }
}
