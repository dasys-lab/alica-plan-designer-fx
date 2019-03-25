package de.unikassel.vs.alica.planDesigner.command.remove;

import de.unikassel.vs.alica.planDesigner.alicamodel.AbstractPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;

public class RemoveAbstractPlanFromState extends Command {
    protected State state;
    protected AbstractPlan abstractPlan;

    public RemoveAbstractPlanFromState(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.state = (State) modelManager.getPlanElement(mmq.getParentId());
        this.abstractPlan = (AbstractPlan) modelManager.getPlanElement(mmq.getElementId());
    }

    @Override
    public void doCommand() {
        this.state.removeAbstractPlan(abstractPlan);
        this.fireEvent(ModelEventType.ELEMENT_REMOVED, abstractPlan);
    }

    @Override
    public void undoCommand() {
        this.state.addAbstractPlan(abstractPlan);
        this.fireEvent(ModelEventType.ELEMENT_ADDED, abstractPlan);
    }
}
