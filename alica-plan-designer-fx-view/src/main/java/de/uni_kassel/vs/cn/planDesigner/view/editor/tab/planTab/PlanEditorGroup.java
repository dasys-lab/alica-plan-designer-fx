package de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTab;

import de.uni_kassel.vs.cn.planDesigner.view.editor.container.*;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.AbstractPlanTab;
import de.uni_kassel.vs.cn.planDesigner.view.model.*;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@link PlanEditorGroup} is the surrounding body for plan visualisation.
 * It expands automatically if new or existing plan elements are brought to the edge of the plan.
 * The visualisation of certain elements is realised through {@link AbstractPlanElementContainer}.
 */
public class PlanEditorGroup extends Group {
    private AbstractPlanTab planEditorTab;
    private PlanModelVisualisationObject planModelVisualisationObject;
    private PlanViewModel plan;
    private Map<Long, StateContainer> stateContainers;
    private Map<Long, TransitionContainer> transitionContainers;
    private Map<Long, EntryPointContainer> entryPointContainers;
    private Map<Long, SynchronisationContainer> synchronisationContainers;

    public PlanEditorGroup(PlanModelVisualisationObject planModelVisualisationObject, AbstractPlanTab planEditorTab) {
        super();
        this.planModelVisualisationObject = planModelVisualisationObject;
        this.planEditorTab = planEditorTab;
        this.plan = planModelVisualisationObject.getPlan();
        setMouseTransparent(false);
        setupPlanVisualisation();
    }

    public void setupPlanVisualisation() {
        getChildren().clear();
        stateContainers = createStateContainers();
        transitionContainers = createTransitionContainers();
        entryPointContainers = createEntryPointContainers();
        synchronisationContainers = createSynchronisationContainers();

        getChildren().addAll(transitionContainers.values());
        getChildren().addAll(stateContainers.values());
        getChildren().addAll(entryPointContainers.values());
        getChildren().addAll(synchronisationContainers.values());

        //TODO add class later
//        this.setOnMouseClicked(new MouseClickHandler(transitionContainers));
    }

    public PlanModelVisualisationObject getPlanModelVisualisationObject() {
        return planModelVisualisationObject;
    }

    public Map<Long, StateContainer> getStateContainers() {
        return stateContainers;
    }

    public Map<Long, TransitionContainer> getTransitionContainers() {
        return transitionContainers;
    }

    public Map<Long, EntryPointContainer> getEntryPointContainers() {
        return entryPointContainers;
    }

    public Map<Long, SynchronisationContainer> getSynchronisationContainers() {
        return synchronisationContainers;
    }

    public AbstractPlanTab getPlanEditorTab() {
        return planEditorTab;
    }

    private Map<Long, EntryPointContainer> createEntryPointContainers() {
        Map<Long, EntryPointContainer> entryPoints = new HashMap<>();
        for (EntryPointViewModel ep : plan.getEntryPoints()) {
            entryPoints.put(ep.getId(), new EntryPointContainer(ep, stateContainers.get(ep.getState().getId())));
        }
        return entryPoints;
    }

//    private PmlUiExtension createPmlUiExtension(PlanElement element, PmlUiExtension pmlUiExtension) {
//        if (pmlUiExtension == null) {
//            pmlUiExtension = EMFModelUtils.getPmlUiExtensionModelFactory().createPmlUiExtension();
//            getPlanModelVisualisationObject().getPmlUiExtensionMap().getExtension().put(element, pmlUiExtension);
//        }
//        return pmlUiExtension;
//    }

    private Map<Long, TransitionContainer> createTransitionContainers() {
        Map<Long, TransitionContainer> transitions = new HashMap<>();
        for (TransitionViewModel trans : plan.getTransitions()) {
            transitions.put(trans.getId(), new TransitionContainer(trans, stateContainers.get(trans.getOutState().getId()),
                    stateContainers.get(trans.getInState().getId())));
        }
        return transitions;
    }

    private Map<Long, StateContainer> createStateContainers() {
        Map<Long, StateContainer> states = new HashMap<>();
        for (StateViewModel state : plan.getStates()) {
            states.put(state.getId(), new StateContainer(state));
        }
        return states;
    }

    private Map<Long, SynchronisationContainer> createSynchronisationContainers() {
        Map<Long, SynchronisationContainer> synchros = new HashMap<>();
        for (SynchronisationViewModel synchronisation : plan.getSynchronisations()) {
            ArrayList<TransitionContainer> synchedTransitions = new ArrayList<>();
            for(TransitionViewModel model : synchronisation.getTransitions()) {
                synchedTransitions.add(transitionContainers.get(model.getId()));
            }
            synchros.put(synchronisation.getId(), new SynchronisationContainer(synchronisation, synchedTransitions));
        }
        return synchros;
    }
}
