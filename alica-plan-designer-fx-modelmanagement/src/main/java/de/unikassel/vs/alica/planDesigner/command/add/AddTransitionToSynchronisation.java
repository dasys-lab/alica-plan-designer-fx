package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.Synchronization;
import de.unikassel.vs.alica.planDesigner.alicamodel.Transition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;

public class AddTransitionToSynchronisation extends AbstractCommand {

    private Transition transitionToAdd;
    private Synchronization synchronization;
    private Plan plan;

    public AddTransitionToSynchronisation(ModelManager manager, Synchronization synchronization, Transition transitionToAdd, Plan plan) {
        super(manager);
        this.transitionToAdd = transitionToAdd;
        this.plan = plan;
        this.synchronization = synchronization;
    }

    @Override
    public void doCommand() {
        synchronization.getSyncedTransitions().add(transitionToAdd);
    }

    @Override
    public void undoCommand() {
        synchronization.getSyncedTransitions().remove(transitionToAdd);
    }
}
