package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Synchronisation;
import de.unikassel.vs.alica.planDesigner.alicamodel.Transition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;

public class RemoveTransitionFromSynchronisation extends AbstractCommand {

    protected Transition transition;
    protected Synchronisation synchronisation;

    public RemoveTransitionFromSynchronisation(ModelManager manager, Synchronisation synchronisation, Transition transition) {
        super(manager);
        this.synchronisation = synchronisation;
        this.transition = transition;
    }

    @Override
    public void doCommand() {
        synchronisation.getSyncedTransitions().remove(transition);
    }

    @Override
    public void undoCommand() {
        synchronisation.getSyncedTransitions().add(transition);
    }
}
