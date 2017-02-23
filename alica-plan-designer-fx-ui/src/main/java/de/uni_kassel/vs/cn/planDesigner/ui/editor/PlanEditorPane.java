package de.uni_kassel.vs.cn.planDesigner.ui.editor;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.EntryPointContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.StateContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.SuccessStateContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.TransitionContainer;
import javafx.scene.Group;
import org.eclipse.emf.ecore.EObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by marci on 23.11.16.
 */
public class PlanEditorPane extends Group {

    private PlanModelVisualisationObject planModelVisualisationObject;
    private List<StateContainer> stateContainers;
    private List<TransitionContainer> transitionContainers;
    private List<EntryPointContainer> entryPointContainers;
    private CommandStack commandStack;
    private EditorTab<Plan> planEditorTab;

    public PlanEditorPane(PlanModelVisualisationObject planModelVisualisationObject, EditorTab<Plan> planEditorTab) {
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

        getChildren().addAll(transitionContainers);
        getChildren().addAll(stateContainers);
        getChildren().addAll(entryPointContainers);

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

    public EditorTab<Plan> getPlanEditorTab() {
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
            TransitionContainer transitionContainer = new TransitionContainer(transition, pmlUiExtension, commandStack, fromState, toState);
            transitions.add(transitionContainer);
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
                    if (e instanceof SuccessState) {
                        return new SuccessStateContainer(pmlUiExtension, e, commandStack);
                    } else {
                        return new StateContainer(pmlUiExtension, e, commandStack);
                    }
                })
                .collect(Collectors.toList());
    }
}
