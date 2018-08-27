package de.uni_kassel.vs.cn.planDesigner.view.editor;

import de.uni_kassel.vs.cn.planDesigner.view.editor.container.*;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.AbstractPlanTab;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link PlanEditorGroup} is the surrounding body for plan visualisation.
 * It expands automatically if new or existing plan elements are brought to the edge of the plan.
 * The visualisation of certain elements is realised through {@link AbstractPlanElementContainer}.
 */
public class PlanEditorGroup extends Group {
    private PlanModelVisualisationObject planModelVisualisationObject;
    private List<AbstractPlanElementContainer> stateContainers;
    private List<AbstractPlanElementContainer> transitionContainers;
    private List<AbstractPlanElementContainer> entryPointContainers;
    private AbstractPlanTab planEditorTab;
    private List<AbstractPlanElementContainer> synchronisationContainers;

    public PlanEditorGroup(PlanModelVisualisationObject planModelVisualisationObject, AbstractPlanTab planEditorTab) {
        super();
        this.planModelVisualisationObject = planModelVisualisationObject;
        this.planEditorTab = planEditorTab;
        setMouseTransparent(false);
        setupPlanVisualisation();
    }

    public void setupPlanVisualisation() {
        getChildren().clear();
        stateContainers = createStateContainers();
        transitionContainers = createTransitionContainers();
        entryPointContainers = createEntryPointContainers();
        synchronisationContainers = createSynchronisationContainers();

        getChildren().addAll(transitionContainers);
        getChildren().addAll(stateContainers);
        getChildren().addAll(entryPointContainers);
        getChildren().addAll(synchronisationContainers);

        //TODO add class later
//        this.setOnMouseClicked(new MouseClickHandler(transitionContainers));
    }

    public PlanModelVisualisationObject getPlanModelVisualisationObject() {
        return planModelVisualisationObject;
    }

    public List<AbstractPlanElementContainer> getStateContainers() {
        return stateContainers;
    }

    public List<AbstractPlanElementContainer> getTransitionContainers() {
        return transitionContainers;
    }

    public List<AbstractPlanElementContainer> getEntryPointContainers() {
        return entryPointContainers;
    }

    public List<AbstractPlanElementContainer> getSynchronisationContainers() {
        return synchronisationContainers;
    }

    public AbstractPlanTab getPlanEditorTab() {
        return planEditorTab;
    }

    private List<AbstractPlanElementContainer> createEntryPointContainers() {
//        return planModelVisualisationObject
//                .getPlan()
//                .getEntryPoints()
//                .stream()
//                .map(e -> {
//                    PmlUiExtension pmlUiExtension = null;
//                    for (Map.Entry<EObject, PmlUiExtension> entry : planModelVisualisationObject.getPmlUiExtensionMap().getExtension()) {
//                        EObject expressionVar = entry.getKey();
//
//                        if (expressionVar instanceof EntryPoint && ((EntryPoint) expressionVar).getId() == e.getId()) {
//                            pmlUiExtension = entry.getValue();
//                            break;
//                        }
//                    }
//                    pmlUiExtension = createPmlUiExtension(e, pmlUiExtension);
//                    Optional<StateContainer> first = stateContainers
//                            .stream()
//                            .filter(s -> e.getState() != null && s.getModelElementId().getId() == e.getState().getId())
//                            .findFirst();
//                    if (first.isPresent()) {
//                        return new EntryPointContainer(e, pmlUiExtension, first.get(), commandStack);
//                    } else {
//                        return new EntryPointContainer(e, pmlUiExtension, null, commandStack);
//                    }
//                }).collect(Collectors.toList());
        return new ArrayList<>();
    }

//    private PmlUiExtension createPmlUiExtension(PlanElement element, PmlUiExtension pmlUiExtension) {
//        if (pmlUiExtension == null) {
//            pmlUiExtension = EMFModelUtils.getPmlUiExtensionModelFactory().createPmlUiExtension();
//            getPlanModelVisualisationObject().getPmlUiExtensionMap().getExtension().put(element, pmlUiExtension);
//        }
//        return pmlUiExtension;
//    }

    private List<AbstractPlanElementContainer> createTransitionContainers() {
//        List<AbstractPlanElementContainer> transitions = new ArrayList<>();
//        for (Transition transition : planModelVisualisationObject.getPlan().getTransitions()) {
//
//            StateContainer fromState = stateContainers.stream()
//                    .filter(e -> e.getModelElementId().equals(transition.getInState()))
//                    .findFirst()
//                    .orElse(null);
//
//            StateContainer toState = stateContainers.stream()
//                    .filter(e -> e.getModelElementId().equals(transition.getOutState()))
//                    .findFirst()
//                    .orElse(null);
//
//            PmlUiExtension pmlUiExtension = planModelVisualisationObject.getPmlUiExtensionMap().getExtension().get(transition);
//            pmlUiExtension = createPmlUiExtension(transition, pmlUiExtension);
//            if(fromState != null && toState != null) {
//                TransitionContainer transitionContainer = new TransitionContainer(transition, pmlUiExtension, commandStack, fromState, toState);
//                transitions.add(transitionContainer);
//            }
//        }
//        return transitions;
        return new ArrayList<>();
    }

    private List<AbstractPlanElementContainer> createStateContainers() {
//        return planModelVisualisationObject
//                .getPlan()
//                .getStates()
//                .stream()
//                .map(e -> {
//                    PmlUiExtension pmlUiExtension = null;
//                    for (Map.Entry<EObject, PmlUiExtension> entry : planModelVisualisationObject.getPmlUiExtensionMap().getExtension()) {
//                        EObject expressionVar = entry.getKey();
//
//                        if (expressionVar instanceof State && ((State) expressionVar).getId() == e.getId()) {
//                            pmlUiExtension = entry.getValue();
//                            break;
//                        }
//                    }
//
//                    pmlUiExtension = createPmlUiExtension(e, pmlUiExtension);
//
//                    if (e instanceof SuccessState) {
//                        return new SuccessStateContainer(pmlUiExtension, e, commandStack);
//                    } else if (e instanceof FailureState) {
//                        return new FailureStateContainer(pmlUiExtension, e, commandStack);
//                    } else {
//                        return new StateContainer(pmlUiExtension, e, commandStack);
//                    }
//                })
//                .collect(Collectors.toList());
        return new ArrayList<>();
    }

    private List<AbstractPlanElementContainer> createSynchronisationContainers() {
//        return planModelVisualisationObject
//                .getPlan()
//                .getSynchronisations()
//                .stream()
//                .map(e -> {
//                    PmlUiExtension pmlUiExtension = null;
//                    for (Map.Entry<EObject, PmlUiExtension> entry : planModelVisualisationObject.getPmlUiExtensionMap().getExtension()) {
//                        EObject expressionVar = entry.getKey();
//
//                        if (expressionVar instanceof Synchronisation && ((Synchronisation) expressionVar).getId() == e.getId()) {
//                            pmlUiExtension = entry.getValue();
//                            break;
//                        }
//                    }
//
//                    pmlUiExtension = createPmlUiExtension(e, pmlUiExtension);
//
//                    List<TransitionContainer> collect = transitionContainers
//                            .stream()
//                            .filter(f -> e.getSynchedTransitions().contains(f.getModelElementId()))
//                            .collect(Collectors.toList());
//                    return new SynchronisationContainer(e,collect, pmlUiExtension, commandStack);
//                })
//                .collect(Collectors.toList());
        return new ArrayList<>();
    }
}
