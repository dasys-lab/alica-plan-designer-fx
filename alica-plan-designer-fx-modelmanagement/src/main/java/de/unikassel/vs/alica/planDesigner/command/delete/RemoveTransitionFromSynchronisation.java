package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Synchronization;
import de.unikassel.vs.alica.planDesigner.alicamodel.Transition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;

public class RemoveTransitionFromSynchronisation extends AbstractCommand {

    protected Transition transition;
    protected Synchronization synchronization;

    public RemoveTransitionFromSynchronisation(ModelManager manager, Synchronization synchronization, Transition transition) {
        super(manager);
        this.synchronization = synchronization;
        this.transition = transition;
    }

    @Override
    public void doCommand() {
        synchronization.getSyncedTransitions().remove(transition);
    }

    @Override
    public void undoCommand() {
        synchronization.getSyncedTransitions().add(transition);
    }
}
