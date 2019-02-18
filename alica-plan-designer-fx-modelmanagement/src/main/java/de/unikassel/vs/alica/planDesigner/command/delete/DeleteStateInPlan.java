package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import de.unikassel.vs.alica.planDesigner.alicamodel.Transition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanUiExtensionPair;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;

import java.util.HashMap;
import java.util.Map;

public class DeleteStateInPlan extends AbstractCommand {

    protected PlanUiExtensionPair parentOfDeleted;

    protected Map<Transition, State> outStatesOfInTransitions = new HashMap<>();
    protected Map<Transition, State> inStatesOfOutTransitions = new HashMap<>();
    protected Map<Transition , UiExtension> pmlUiExtensionsOfTransitions = new HashMap<>();
    protected UiExtension uiExtension;
    protected State state;

    public DeleteStateInPlan(ModelManager modelManager, State state, PlanUiExtensionPair parentOfDeleted) {
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
        
        parentOfDeleted.remove(state);

        pmlUiExtensionsOfTransitions
                .keySet()
                .forEach(k -> parentOfDeleted.remove(k));
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
        uiExtension = parentOfDeleted.getUiExtension(state);


        // save pml view extensions of transitions, if they have any
        outStatesOfInTransitions
                .keySet()
                .forEach(k -> {
                    UiExtension uiExtension = parentOfDeleted.getUiExtension(k);
                    if (uiExtension != null) {
                        pmlUiExtensionsOfTransitions.put(k, uiExtension);
                    }
                });

        inStatesOfOutTransitions
                .keySet()
                .forEach(k -> {
                    UiExtension uiExtension = parentOfDeleted.getUiExtension(k);
                    if (uiExtension != null) {
                        pmlUiExtensionsOfTransitions.put(k, uiExtension);
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

        parentOfDeleted.put(state, new UiExtension());
        pmlUiExtensionsOfTransitions
                .entrySet()
                .forEach(e -> parentOfDeleted.put(e.getKey(), e.getValue()));
    }
}
