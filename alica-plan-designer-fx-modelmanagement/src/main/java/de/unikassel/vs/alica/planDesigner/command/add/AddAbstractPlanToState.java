package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.AbstractPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;

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
        state.addAbstractPlan(abstractPlan);
    }

    @Override
    public void undoCommand() {
        state.removeAbstractPlan(abstractPlan);
    }
}
