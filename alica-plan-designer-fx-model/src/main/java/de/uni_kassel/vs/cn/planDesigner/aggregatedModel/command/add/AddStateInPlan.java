package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.Command;
import de.uni_kassel.vs.cn.planDesigner.alica.State;

/**
 * Created by marci on 02.12.16.
 */
public class AddStateInPlan extends Command<State> {

    private PlanModelVisualisationObject parentOfElement;

    public AddStateInPlan(State element, PlanModelVisualisationObject parentOfElement) {
        super(element);
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlan().getStates().add(getElementToEdit());
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlan().getStates().remove(getElementToEdit());
    }

    @Override
    public String getCommandString() {
        return "Add State to Plan " + getElementToEdit();
    }
}
