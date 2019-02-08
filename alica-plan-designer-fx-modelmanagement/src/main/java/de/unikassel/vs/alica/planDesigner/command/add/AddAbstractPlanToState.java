package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.AbstractPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.events.ModelEvent;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class AddAbstractPlanToState extends AbstractCommand {
    protected State state;
    protected AbstractPlan abstractPlan;

    public AddAbstractPlanToState(ModelManager modelManager, AbstractPlan abstractPlan, State state) {
        super(modelManager);
        this.abstractPlan = abstractPlan;
        this.state = state;
    }

    @Override
    public void doCommand() {
        //Don't add AbstractPlan, if the same existing
        for (AbstractPlan existingAbstractPlan: state.getPlans()) {
            if (existingAbstractPlan.getId() == abstractPlan.getId()) {
                return;
            }
        }
        state.addAbstractPlan(abstractPlan);

        //event for updateView
        Plan plan = (Plan) state.getParentPlan();
        ModelEvent event = new ModelEvent(ModelEventType.ELEMENT_ADD, plan, Types.ABSTRACTPLAN);
        event.setParentId(abstractPlan.getId());
        event.setNewValue(state);
        modelManager.fireEvent(event);
    }

    @Override
    public void undoCommand() {
        state.removeAbstractPlan(abstractPlan);
    }
}
