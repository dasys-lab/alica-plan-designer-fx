package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronization;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

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
        synchronization.getSynchedTransitions().add(transitionToAdd);
    }

    @Override
    public void undoCommand() {
        synchronization.getSynchedTransitions().remove(transitionToAdd);
    }

    @Override
    public String getCommandString() {
        return "Add Transition to  Synchronisation";
    }
}
