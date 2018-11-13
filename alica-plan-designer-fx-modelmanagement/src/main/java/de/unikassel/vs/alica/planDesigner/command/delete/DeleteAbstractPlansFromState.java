package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.AbstractPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;

public class DeleteAbstractPlansFromState extends AbstractCommand {

    private final State state;
    private final AbstractPlan element;

    public DeleteAbstractPlansFromState(ModelManager manager, AbstractPlan element, State state) {
        super(manager);
        this.state = state;
        this.element = element;
    }

    @Override
    public void doCommand() {
        state.getPlans().remove(element);
    }

    @Override
    public void undoCommand() {
        state.getPlans().add(element);
    }
}
