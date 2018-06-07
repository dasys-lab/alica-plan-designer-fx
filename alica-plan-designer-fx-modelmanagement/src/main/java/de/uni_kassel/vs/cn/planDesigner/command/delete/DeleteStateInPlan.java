package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.State;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PmlUiExtension;

import java.util.HashMap;
import java.util.Map;

public class DeleteStateInPlan extends AbstractCommand {

    protected PlanModelVisualisationObject parentOfDeleted;

    protected Map<Transition, State> outStatesOfInTransitions = new HashMap<>();
    protected Map<Transition, State> inStatesOfOutTransitions = new HashMap<>();
    protected Map<Transition ,PmlUiExtension> pmlUiExtensionsOfTransitions = new HashMap<>();
    protected PmlUiExtension pmlUiExtension;
    protected State state;

    public DeleteStateInPlan(ModelManager modelManager, State state, PlanModelVisualisationObject parentOfDeleted) {
        super(modelManager);
        this.parentOfDeleted = parentOfDeleted;
        this.state = state;
    }

    @Override
    public void doCommand() {
        outStatesOfInTransitions.clear();
        inStatesOfOutTransitions.clear();
        pmlUiExtensionsOfTransitions.clear();

        // save for later retrieval if needed
        saveForLaterRetrieval();

        // Delete
        delete();
    }

    /**
     * The actual deletion of the state and its associated transitions
     * <p></p>
     * IMPORTANT this also includes pml view extensions
     */
    private void delete() {
        parentOfDeleted.getPlan().getStates().remove(state);
        parentOfDeleted.getPlan().getTransitions().removeAll(inStatesOfOutTransitions.keySet());
        parentOfDeleted.getPlan().getTransitions().removeAll(outStatesOfInTransitions.keySet());

        for (Transition outTransition : inStatesOfOutTransitions.keySet()) {
            outTransition.setInState(null);
        }

        for (Transition inTransition : outStatesOfInTransitions.keySet()) {
            inTransition.setOutState(null);
        }
        
        parentOfDeleted.getPmlUiExtensionMap().getExtension().remove(state);

        pmlUiExtensionsOfTransitions
                .keySet()
                .forEach(k -> parentOfDeleted.getPmlUiExtensionMap().getExtension().remove(k));
        }

    /**
     * This method ensures the deleted data can be retrieved if this command is undone
     */
    private void saveForLaterRetrieval() {
        // save in transitions and their out states
        parentOfDeleted.getPlan()
                .getTransitions()
                .stream()
                .filter(e -> e.getInState().equals(state))
                .forEach(t -> outStatesOfInTransitions.put(t, t.getOutState()));

        // save out transitions and their in states
        parentOfDeleted.getPlan()
                .getTransitions()
                .stream()
                .filter(e -> e.getOutState().equals(state))
                .forEach(t -> inStatesOfOutTransitions.put(t, t.getInState()));

        // save pml view extension of state
        pmlUiExtension = parentOfDeleted.getPmlUiExtensionMap().getExtension().get(state);


        // save pml view extensions of transitions, if they have any
        outStatesOfInTransitions
                .keySet()
                .forEach(k -> {
                    PmlUiExtension pmlUiExtension = parentOfDeleted.getPmlUiExtensionMap().getExtension().get(k);
                    if (pmlUiExtension != null) {
                        pmlUiExtensionsOfTransitions.put(k, pmlUiExtension);
                    }
                });

        inStatesOfOutTransitions
                .keySet()
                .forEach(k -> {
                    PmlUiExtension pmlUiExtension = parentOfDeleted.getPmlUiExtensionMap().getExtension().get(k);
                    if (pmlUiExtension != null) {
                        pmlUiExtensionsOfTransitions.put(k, pmlUiExtension);
                    }
                });
    }

    @Override
    public void undoCommand() {
        parentOfDeleted.getPlan().getStates().add(state);
        parentOfDeleted.getPlan().getTransitions().addAll(outStatesOfInTransitions.keySet());
        parentOfDeleted.getPlan().getTransitions().addAll(inStatesOfOutTransitions.keySet());

        for (Transition outTransition : inStatesOfOutTransitions.keySet()) {
            outTransition.setInState(inStatesOfOutTransitions.get(outTransition));
        }

        for (Transition inTransition : outStatesOfInTransitions.keySet()) {
            inTransition.setOutState(outStatesOfInTransitions.get(inTransition));
        }

        parentOfDeleted.getPmlUiExtensionMap().getExtension().put(state, new PmlUiExtension());
        pmlUiExtensionsOfTransitions
                .entrySet()
                .forEach(e -> parentOfDeleted.getPmlUiExtensionMap().getExtension().put(e.getKey(), e.getValue()));
    }

    @Override
    public String getCommandString() {
        return "Delete State " + state.getName() + " in Plan " + parentOfDeleted.getPlan().getName();
    }
}
