package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.delete;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.Command;
import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;

import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marci on 01.12.16.
 */
public class DeleteStateInPlan extends Command<State> {

    private PlanModelVisualisationObject parentOfDeleted;

    private List<Transition> deletedInTransitions;
    private List<Transition> deletedOutTransitions;
    private PmlUiExtension pmlUiExtension;

    public DeleteStateInPlan(State state, PlanModelVisualisationObject parentOfDeleted) {
        super(state);
        this.parentOfDeleted = parentOfDeleted;
    }
    @Override
    public void doCommand() {
        // save for later retrieval if needed
        saveForLaterRetrieval();

        // Delete
        delete();

    }

    private void delete() {
        parentOfDeleted.getPlan().getStates().remove(getElementToEdit());
        parentOfDeleted.getPlan().getTransitions().removeAll(deletedInTransitions);
        parentOfDeleted.getPlan().getTransitions().removeAll(deletedOutTransitions);
    }

    private void saveForLaterRetrieval() {
        deletedInTransitions = parentOfDeleted.getPlan()
                .getTransitions()
                .stream()
                .filter(e -> e.getInState().equals(getElementToEdit()))
                .collect(Collectors.toList());

        deletedOutTransitions = parentOfDeleted.getPlan()
                .getTransitions()
                .stream()
                .filter(e -> e.getOutState().equals(getElementToEdit()))
                .collect(Collectors.toList());

        pmlUiExtension = parentOfDeleted.getPmlUiExtensionMap().getExtension().get(getElementToEdit());
    }

    @Override
    public void undoCommand() {
        parentOfDeleted.getPlan().getStates().add(getElementToEdit());
        parentOfDeleted.getPlan().getTransitions().addAll(deletedInTransitions);
        parentOfDeleted.getPlan().getTransitions().addAll(deletedOutTransitions);
        parentOfDeleted.getPmlUiExtensionMap().getExtension().add(new AbstractMap.SimpleEntry<>(getElementToEdit(), pmlUiExtension));
    }

    @Override
    public String getCommandString() {
        return "Delete State " + getElementToEdit().getName() + " in Plan " + parentOfDeleted.getPlan().getName();
    }
}
