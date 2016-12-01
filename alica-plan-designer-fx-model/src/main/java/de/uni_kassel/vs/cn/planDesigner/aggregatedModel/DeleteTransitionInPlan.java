package de.uni_kassel.vs.cn.planDesigner.aggregatedModel;

import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.Transition;

/**
 * Created by marci on 01.12.16.
 */
public class DeleteTransitionInPlan extends Command<Transition> {

    private Plan parentOfDeleted;

    public DeleteTransitionInPlan(Transition element) {
        super(element);
    }

    @Override
    public void doCommand() {
        parentOfDeleted.getTransitions().remove(getElementToEdit());
    }

    @Override
    public void undoCommand() {

    }
}
