package de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab;

import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.container.*;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.AbstractPlanTab;
import de.unikassel.vs.alica.planDesigner.view.model.*;
import javafx.collections.ListChangeListener;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@link PlanEditorGroup} is the surrounding body for plan visualisation.
 * It expands automatically if new or existing plan elements are brought to the edge of the plan.
 * The visualisation of certain elements is realised through {@link AbstractPlanElementContainer}.
 */
public class PlanEditorGroup extends Group {
    private PlanTab planEditorTab;
    private PlanViewModel plan;
    private Map<Long, StateContainer> stateContainers;
    private Map<Long, TransitionContainer> transitionContainers;
    private Map<Long, EntryPointContainer> entryPointContainers;
    private Map<Long, SynchronizationContainer> synchronizationContainers;

    public PlanEditorGroup(PlanViewModel plan, PlanTab planEditorTab) {
        super();
        this.planEditorTab = planEditorTab;
        this.plan = plan;
        setMouseTransparent(false);
        setupPlanVisualisation();
    }

    public void setupPlanVisualisation() {
        getChildren().clear();
        stateContainers = createStateContainers();
        transitionContainers = createTransitionContainers();
        entryPointContainers = createEntryPointContainers();
        synchronizationContainers = createSynchronisationContainers();

        getChildren().addAll(transitionContainers.values());
        getChildren().addAll(stateContainers.values());
        getChildren().addAll(entryPointContainers.values());
        getChildren().addAll(synchronizationContainers.values());

        createStateListeners();
        createEntryPointListeners();
        createTransitionListeners();
        createSynchronizationListeners();
        //TODO: create Listeners for other types

        //TODO add class later
//        this.setOnMouseClicked(new MouseClickHandler(transitionContainers));
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

    public Map<Long, SynchronizationContainer> getSynchronizationContainers() {
        return synchronizationContainers;
    }

    public AbstractPlanTab getPlanEditorTab() {
        return planEditorTab;
    }

    private Map<Long, EntryPointContainer> createEntryPointContainers() {
        Map<Long, EntryPointContainer> entryPoints = new HashMap<>();
        for (EntryPointViewModel ep : plan.getEntryPoints()) {
            entryPoints.put(ep.getId(), new EntryPointContainer(ep, ep.getState() == null ? null : stateContainers.get(ep.getState().getId()), planEditorTab));
        }
        return entryPoints;
    }

    private Map<Long, TransitionContainer> createTransitionContainers() {
        Map<Long, TransitionContainer> transitions = new HashMap<>();
        for (TransitionViewModel trans : plan.getTransitions()) {
            transitions.put(trans.getId(), new TransitionContainer(trans, stateContainers.get(trans.getInState().getId()),
                    stateContainers.get(trans.getOutState().getId()), planEditorTab));
        }
        return transitions;
    }

    private Map<Long, StateContainer> createStateContainers() {
        Map<Long, StateContainer> states = new HashMap<>();
        for (StateViewModel state : plan.getStates()) {
            states.put(state.getId(), createStateContainer(state, planEditorTab));
        }
        return states;
    }

    private Map<Long, SynchronizationContainer> createSynchronisationContainers() {
        Map<Long, SynchronizationContainer> synchros = new HashMap<>();
        for (SynchronizationViewModel synchronisation : plan.getSynchronisations()) {
            ArrayList<TransitionContainer> synchedTransitions = new ArrayList<>();
            for(TransitionViewModel model : synchronisation.getTransitions()) {
                synchedTransitions.add(transitionContainers.get(model.getId()));
            }
            synchros.put(synchronisation.getId(), new SynchronizationContainer(synchronisation, synchedTransitions, planEditorTab));
        }
        return synchros;
    }

    private void createStateListeners() {
        plan.getStates().addListener((ListChangeListener<StateViewModel>) c -> {
            while(c.next()){
                if(c.wasAdded()) {
                    for(StateViewModel state : c.getAddedSubList()){
                        StateContainer stateContainer = createStateContainer(state, planEditorTab);
                        stateContainers.put(state.getId(), stateContainer);
                        getChildren().add(stateContainer);
                        int x = state.getXPosition();
                        int y = state.getYPosition();
                        stateContainer.setLayoutX(x);
                        stateContainer.setLayoutY(y);
                    }
                }else if(c.wasRemoved()){
                    for(StateViewModel state : c.getRemoved()){
                        StateContainer stateContainer = stateContainers.remove(state.getId());
                        getChildren().remove(stateContainer);
                    }
                }
            }
        });
    }

    private StateContainer createStateContainer(StateViewModel state, PlanTab planTab) {
        switch(state.getType()){
            case Types.STATE:
                return new StateContainer(state, planTab);
            case Types.SUCCESSSTATE:
                return new SuccessStateContainer(state, planTab);
            case Types.FAILURESTATE:
                return new FailureStateContainer(state, planTab);
            default:
                throw new IllegalStateException("State has a non-state type!");
        }
    }

    private void createEntryPointListeners(){
        plan.getEntryPoints().addListener((ListChangeListener<EntryPointViewModel>) c -> {
            while(c.next()){
                if(c.wasAdded()){
                    for(EntryPointViewModel entryPoint : c.getAddedSubList()){
                        StateContainer stateContainer = null;
                        if(entryPoint.getState() != null){
                            stateContainer = stateContainers.get(entryPoint.getState().getId());
                        }
                        EntryPointContainer entryPointContainer = new EntryPointContainer(entryPoint, stateContainer, planEditorTab);
                        entryPointContainers.put(entryPoint.getId(), entryPointContainer);
                        getChildren().add(entryPointContainer);
                        int x = entryPoint.getXPosition();
                        int y = entryPoint.getYPosition();
                        entryPointContainer.setLayoutX(x);
                        entryPointContainer.setLayoutY(y);
                    }
                }else if(c.wasRemoved()){
                    for(EntryPointViewModel entryPoint : c.getRemoved()){
                        EntryPointContainer entryPointContainer = entryPointContainers.remove(entryPoint.getId());
                        getChildren().remove(entryPointContainer);
                    }

                }
            }
        });
    }

    private void createTransitionListeners(){
       plan.getTransitions().addListener((ListChangeListener<TransitionViewModel>) c -> {
            while (c.next()) {
                if(c.wasAdded()) {
                    for(TransitionViewModel transition : c.getAddedSubList()) {
                        StateContainer fromState = stateContainers.get(transition.getInState().getId());
                        StateContainer toState = stateContainers.get(transition.getOutState().getId());
                        TransitionContainer transitionContainer = new TransitionContainer(transition, fromState, toState, planEditorTab);
                        transitionContainers.put(transition.getId(), transitionContainer);

                        // Has to be with index because otherwise it would be drawn on top of states
                        getChildren().add(0, transitionContainer);
                    }
                } else if (c.wasRemoved()) {
                    for (TransitionViewModel transition : c.getRemoved()) {
                        TransitionContainer transitionContainer = transitionContainers.remove(transition.getId());
                        getChildren().remove(transitionContainer);
                    }
                }
            }
       });
    }

    private void createSynchronizationListeners() {
        plan.getSynchronisations().addListener((ListChangeListener<SynchronizationViewModel>) c -> {
            while (c.next()) {
                if(c.wasAdded()) {
                    for(SynchronizationViewModel sync : c.getAddedSubList()) {
                        ArrayList<TransitionContainer> synchedTransitions = new ArrayList<>();
                        for(TransitionViewModel model : sync.getTransitions()) {
                            synchedTransitions.add(transitionContainers.get(model.getId()));
                        }
                        SynchronizationContainer syncContainer = new SynchronizationContainer(sync, synchedTransitions, planEditorTab);
                        synchronizationContainers.put(sync.getId(), syncContainer);

                        getChildren().add(syncContainer);
                    }
                } else if (c.wasRemoved()) {
                    for (SynchronizationViewModel sync : c.getRemoved()) {
                        SynchronizationContainer syncContainer = synchronizationContainers.remove(sync.getId());
                        getChildren().remove(syncContainer);
                    }
                }
            }
        });
    }
}
