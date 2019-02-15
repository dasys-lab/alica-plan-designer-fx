package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.Synchronisation;
import de.unikassel.vs.alica.planDesigner.alicamodel.Transition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;

public class AddTransitionToSynchronisation extends AbstractCommand {

    private Transition transitionToAdd;
    private Synchronisation synchronisation;
    private Plan plan;

    public AddTransitionToSynchronisation(ModelManager manager, Synchronisation synchronisation, Transition transitionToAdd, Plan plan) {
        super(manager);
        this.transitionToAdd = transitionToAdd;
        this.plan = plan;
        this.synchronisation = synchronisation;
    }

    @Override
    public void doCommand() {
        synchronisation.getSyncedTransitions().add(transitionToAdd);
    }

    @Override
    public void undoCommand() {
        synchronisation.getSyncedTransitions().remove(transitionToAdd);
    }
}
