package de.uni_kassel.vs.cn.planDesigner.ui.editor;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.*;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.AbstractEditorTab;
import javafx.scene.Group;
import org.eclipse.emf.ecore.EObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The {@link PlanEditorGroup} is the surrounding body for plan visualisation.
 * It expands automatically if new or existing plan elements are brought to the edge of the plan.
 * The visualisation of certain elements is realised through {@link AbstractPlanElementContainer}.
 */
public class PlanEditorGroup extends Group {

    private PlanModelVisualisationObject planModelVisualisationObject;
    private List<StateContainer> stateContainers;
    private List<TransitionContainer> transitionContainers;
    private List<EntryPointContainer> entryPointContainers;
    private CommandStack commandStack;
    private AbstractEditorTab<Plan> planEditorTab;
    private List<SynchronisationContainer> synchronisationContainers;

    public PlanEditorGroup(PlanModelVisualisationObject planModelVisualisationObject, AbstractEditorTab<Plan> planEditorTab) {
        super();
        this.planModelVisualisationObject = planModelVisualisationObject;
        this.planEditorTab = planEditorTab;
        commandStack = planEditorTab.getCommandStack();
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

        this.setOnMouseClicked(new MouseClickHandler(transitionContainers));
        //getChildren().add(new Line(EditorConstants.PLAN_SHIFTING_PARAMETER + 20d, 0d, EditorConstants.PLAN_SHIFTING_PARAMETER + 20d, getHeight()));
    }

    public CommandStack getCommandStack() {
        return commandStack;
    }

    public PlanModelVisualisationObject getPlanModelVisualisationObject() {
        return planModelVisualisationObject;
    }

    public List<StateContainer> getStateContainers() {
        return stateContainers;
    }

    public AbstractEditorTab<Plan> getPlanEditorTab() {
        return planEditorTab;
    }

    private List<EntryPointContainer> createEntryPointContainers() {
        return planModelVisualisationObject
                .getPlan()
                .getEntryPoints()
                .stream()
                .map(e -> {
                    PmlUiExtension pmlUiExtension = null;
                    for (Map.Entry<EObject, PmlUiExtension> entry : planModelVisualisationObject.getPmlUiExtensionMap().getExtension()) {
                        EObject expressionVar = entry.getKey();

                        if (expressionVar instanceof EntryPoint && ((EntryPoint) expressionVar).getId() == e.getId()) {
                            pmlUiExtension = entry.getValue();
                            break;
                        }
                    }
                    pmlUiExtension = createPmlUiExtension(e, pmlUiExtension);
                    Optional<StateContainer> first = stateContainers
                            .stream()
                            .filter(s -> e.getState() != null && s.getContainedElement().getId() == e.getState().getId())
                            .findFirst();
                    if (first.isPresent()) {
                        return new EntryPointContainer(e, pmlUiExtension, first.get(), commandStack);
                    } else {
                        return new EntryPointContainer(e, pmlUiExtension, null, commandStack);
                    }
                }).collect(Collectors.toList());
    }

    private PmlUiExtension createPmlUiExtension(PlanElement element, PmlUiExtension pmlUiExtension) {
        if (pmlUiExtension == null) {
            pmlUiExtension = EMFModelUtils.getPmlUiExtensionModelFactory().createPmlUiExtension();
            getPlanModelVisualisationObject().getPmlUiExtensionMap().getExtension().put(element, pmlUiExtension);
        }
        return pmlUiExtension;
    }

    private List<TransitionContainer> createTransitionContainers() {
        List<TransitionContainer> transitions = new ArrayList<>();
        for (Transition transition : planModelVisualisationObject.getPlan().getTransitions()) {

            StateContainer fromState = stateContainers.stream()
                    .filter(e -> e.getContainedElement().equals(transition.getInState()))
                    .findFirst()
                    .orElse(null);

            StateContainer toState = stateContainers.stream()
                    .filter(e -> e.getContainedElement().equals(transition.getOutState()))
                    .findFirst()
                    .orElse(null);

            PmlUiExtension pmlUiExtension = planModelVisualisationObject.getPmlUiExtensionMap().getExtension().get(transition);
            pmlUiExtension = createPmlUiExtension(transition, pmlUiExtension);
            if(fromState != null && toState != null) {
                TransitionContainer transitionContainer = new TransitionContainer(transition, pmlUiExtension, commandStack, fromState, toState);
                transitions.add(transitionContainer);
            }
        }
        return transitions;
    }

    private List<StateContainer> createStateContainers() {
        return planModelVisualisationObject
                .getPlan()
                .getStates()
                .stream()
                .map(e -> {
                    PmlUiExtension pmlUiExtension = null;
                    for (Map.Entry<EObject, PmlUiExtension> entry : planModelVisualisationObject.getPmlUiExtensionMap().getExtension()) {
                        EObject expressionVar = entry.getKey();

                        if (expressionVar instanceof State && ((State) expressionVar).getId() == e.getId()) {
                            pmlUiExtension = entry.getValue();
                            break;
                        }
                    }

                    pmlUiExtension = createPmlUiExtension(e, pmlUiExtension);

                    if (e instanceof SuccessState) {
                        return new SuccessStateContainer(pmlUiExtension, e, commandStack);
                    } else if (e instanceof FailureState) {
                        return new FailureStateContainer(pmlUiExtension, e, commandStack);
                    } else {
                        return new StateContainer(pmlUiExtension, e, commandStack);
                    }
                })
                .collect(Collectors.toList());
    }

    private List<SynchronisationContainer> createSynchronisationContainers() {
        return planModelVisualisationObject
                .getPlan()
                .getSynchronisations()
                .stream()
                .map(e -> {
                    PmlUiExtension pmlUiExtension = null;
                    for (Map.Entry<EObject, PmlUiExtension> entry : planModelVisualisationObject.getPmlUiExtensionMap().getExtension()) {
                        EObject expressionVar = entry.getKey();

                        if (expressionVar instanceof Synchronisation && ((Synchronisation) expressionVar).getId() == e.getId()) {
                            pmlUiExtension = entry.getValue();
                            break;
                        }
                    }

                    pmlUiExtension = createPmlUiExtension(e, pmlUiExtension);

                    List<TransitionContainer> collect = transitionContainers
                            .stream()
                            .filter(f -> e.getSynchedTransitions().contains(f.getContainedElement()))
                            .collect(Collectors.toList());
                    return new SynchronisationContainer(e,collect, pmlUiExtension, commandStack);
                })
                .collect(Collectors.toList());
    }
}
