package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronization;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

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
