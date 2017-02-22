package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.delete;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.Command;
import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.impl.EObjectToPmlUiExtensionMapEntryImpl;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.impl.PmlUIExtensionModelFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by marci on 01.12.16.
 */
public class DeleteStateInPlan extends Command<State> {

    private PlanModelVisualisationObject parentOfDeleted;

    private Map<Transition, State> outStatesOfInTransitions = new HashMap<>();
    private Map<Transition, State> inStatesOfOutTransitions = new HashMap<>();

    private Map<Transition ,PmlUiExtension> pmlUiExtensionsOfTransitions = new HashMap<>();

    private PmlUiExtension pmlUiExtension;

    public DeleteStateInPlan(State state, PlanModelVisualisationObject parentOfDeleted) {
        super(state);
        this.parentOfDeleted = parentOfDeleted;
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
     * IMPORTANT this also includes pml ui extensions
     */
    private void delete() {
        parentOfDeleted.getPlan().getStates().remove(getElementToEdit());
        parentOfDeleted.getPlan().getTransitions().removeAll(inStatesOfOutTransitions.keySet());
        parentOfDeleted.getPlan().getTransitions().removeAll(outStatesOfInTransitions.keySet());

        for (Transition outTransition : inStatesOfOutTransitions.keySet()) {
            outTransition.setInState(null);
        }

        for (Transition inTransition : outStatesOfInTransitions.keySet()) {
            inTransition.setOutState(null);
        }
        parentOfDeleted.getPmlUiExtensionMap().getExtension().remove(getElementToEdit());

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
                .filter(e -> e.getInState().equals(getElementToEdit()))
                .forEach(t -> outStatesOfInTransitions.put(t, t.getOutState()));

        // save out transitions and their in states
        parentOfDeleted.getPlan()
                .getTransitions()
                .stream()
                .filter(e -> e.getOutState().equals(getElementToEdit()))
                .forEach(t -> inStatesOfOutTransitions.put(t, t.getInState()));

        // save pml ui extension of state
        pmlUiExtension = parentOfDeleted.getPmlUiExtensionMap().getExtension().get(getElementToEdit());


        // save pml ui extensions of transitions, if they have any
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
        parentOfDeleted.getPlan().getStates().add(getElementToEdit());
        parentOfDeleted.getPlan().getTransitions().addAll(outStatesOfInTransitions.keySet());
        parentOfDeleted.getPlan().getTransitions().addAll(inStatesOfOutTransitions.keySet());

        for (Transition outTransition : inStatesOfOutTransitions.keySet()) {
            outTransition.setInState(inStatesOfOutTransitions.get(outTransition));
        }

        for (Transition inTransition : outStatesOfInTransitions.keySet()) {
            inTransition.setOutState(outStatesOfInTransitions.get(inTransition));
        }

        Map.Entry<EObject, PmlUiExtension> eObjectToPmlUiExtensionMapEntry = ((PmlUIExtensionModelFactoryImpl) EMFModelUtils.getPmlUiExtensionModelFactory()).createEObjectToPmlUiExtensionMapEntry();
        ((EObjectToPmlUiExtensionMapEntryImpl)eObjectToPmlUiExtensionMapEntry).setKey(getElementToEdit());
        eObjectToPmlUiExtensionMapEntry.setValue(pmlUiExtension);
        parentOfDeleted.getPmlUiExtensionMap().getExtension().add(eObjectToPmlUiExtensionMapEntry);
        pmlUiExtensionsOfTransitions
                .entrySet()
                .forEach(e -> parentOfDeleted.getPmlUiExtensionMap().getExtension().put(e.getKey(), e.getValue()));
    }

    @Override
    public String getCommandString() {
        return "Delete State " + getElementToEdit().getName() + " in Plan " + parentOfDeleted.getPlan().getName();
    }
}
