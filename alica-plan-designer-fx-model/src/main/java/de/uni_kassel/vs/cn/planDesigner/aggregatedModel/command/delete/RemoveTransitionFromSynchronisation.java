package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.delete;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.Synchronisation;
import de.uni_kassel.vs.cn.planDesigner.alica.Transition;

/**
 * Created by marci on 22.03.17.
 */
public class RemoveTransitionFromSynchronisation extends AbstractCommand<Synchronisation> {

    private Transition toRemove;

    public RemoveTransitionFromSynchronisation(Synchronisation element, Transition toRemove) {
        super(element, (PlanElement) element.eResource().getContents().get(0));
        this.toRemove = toRemove;
    }

    @Override
    public void doCommand() {
        getElementToEdit().getSynchedTransitions().remove(toRemove);
    }

    @Override
    public void undoCommand() {
        getElementToEdit().getSynchedTransitions().add(toRemove);
    }

    @Override
    public String getCommandString() {
        return "Remove Transition from Synchronisation";
    }
}
