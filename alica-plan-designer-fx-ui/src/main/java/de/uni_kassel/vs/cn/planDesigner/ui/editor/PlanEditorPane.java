package de.uni_kassel.vs.cn.planDesigner.ui.editor;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import javafx.geometry.Insets;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marci on 23.11.16.
 */
public class PlanEditorPane extends AnchorPane {

    private PlanModelVisualisationObject planModelVisualisationObject;
    private List<StateContainer> stateContainers;
    private List<TransitionContainer> transitionContainers;

    public PlanEditorPane(PlanModelVisualisationObject planModelVisualisationObject) {
        super();
        this.planModelVisualisationObject = planModelVisualisationObject;
        addDragDropSupport();
        visualize();
    }

    private void visualize() {
        stateContainers = createStateContainers();
        transitionContainers = createTransitionContainers();
        //getChildren().addAll(stateContainers);
        getChildren().addAll(transitionContainers.stream().map(e -> e.getLine()).collect(Collectors.toList()));
    }

    private List<TransitionContainer> createTransitionContainers() {
        List<TransitionContainer> transitions = new ArrayList<>();
        // there is something whacky about the ids of states in transitions,
        // from the perspective of transitions they don't match up which forced me to program this nightmare
        // 14.12.2016 Scratch that the problem is only solvable via means off EMF
        /*planModelVisualisationObject
                .getPlan()
                .getTransitions()
                .forEach(f -> {
                    planModelVisualisationObject
                            .getPlan()
                            .getStates()
                            .forEach( state -> {
                                if (f.getInState().eIsProxy() && state.getId() == Long.valueOf(((InternalEObject) f.getInState()).eProxyURI().fragment())) {
                                    f.setOutState(state);
                                }

                                if (f.getOutState().eIsProxy() && state.getId() == Long.valueOf(((InternalEObject) f.getOutState()).eProxyURI().fragment())) {
                                    f.setInState(state);
                                }
                            });
                });

        for (Transition transition : planModelVisualisationObject.getPlan().getTransitions()) {

            TransitionContainer transitionContainer = new TransitionContainer(transition, null);

            transitionContainer.setFromState(stateContainers.stream()
                    .filter(e -> e.getState().equals(transition.getInState()))
                    .findFirst()
                    .orElse(null));

            transitionContainer.setToState(stateContainers.stream()
                    .filter(e -> e.getState().equals(transition.getOutState()))
                    .findFirst()
                    .orElse(null));
            transitionContainer.initLine();
            transitions.add(transitionContainer);
        }*/
        return  transitions;/*
        return planModelVisualisationObject
                .getPlan()
                .getStates()
                .forEach()
                .stream()
                .map(e -> e.getOutTransitions().stream())
                .
                .map(e -> {
                    PmlUiExtension pmlUiExtension = planModelVisualisationObject
                            .getPmlUiExtensionMap()
                            .getExtension()
                            .stream()
                            .filter(f -> {
                                String s = f.getKey().toString().split("#")[1];
                                return Long.valueOf(s.substring(0, s.lastIndexOf(')'))) == e.getId();
                            })
                            .findFirst().get().getValue();
                    StateContainer fromState = stateContainers
                            .stream()
                            .filter(t -> t.getState().getId() == e.getOutState().getId())
                            .findFirst().orElse(null);
                    if (fromState == null) {
                        return null;
                    } else {
                        StateContainer toState = stateContainers
                                .stream()
                                .filter(t -> t.getState().getId() == e.getInState().getId())
                                .findFirst().get();
                        return new TransitionContainer(e, fromState, toState, pmlUiExtension);
                    }

                })
                .filter(e -> e != null)
                .collect(Collectors.toList());*/
    }

    private List<StateContainer> createStateContainers() {
        return planModelVisualisationObject
                    .getPlan()
                    .getStates()
                    .stream()
                    .map(e -> {
                        PmlUiExtension pmlUiExtension = planModelVisualisationObject
                                .getPmlUiExtensionMap()
                                .getExtension()
                                .stream()
                                .filter(f -> {
                                    if (f.getKey() instanceof State == false) {
                                        return false;
                                    }
                                    return ((State)f.getKey()).getId() == e.getId();
                                })
                                .findFirst().get().getValue();
                        return new StateContainer(pmlUiExtension, e);
                    })
                    .collect(Collectors.toList());
    }

    private void addDragDropSupport() {
        setOnDragOver(event -> {
    /* data is dragged over the target */
    /* accept it only if it is not dragged from the same node
     * and if it has a string data */
            if (event.getGestureSource() != PlanEditorPane.this &&
                    event.getDragboard().hasString()) {
        /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.ANY);
            }

            event.consume();
        });
        setOnDragEntered(event -> {
/* the drag-and-drop gesture entered the target */
/* show to the user that it is an actual gesture target */
            if (event.getGestureSource() != PlanEditorPane.this &&
                    event.getDragboard().hasString()) {
                PlanEditorPane.this.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
            }

            event.consume();
        });

        setOnDragExited(event -> {
    /* mouse moved away, remove the graphical cues */
            PlanEditorPane.this.setBackground(null);
            event.consume();
        });


        setOnDragDropped(event -> {
    /* data dropped */
    /* if there is a string data on dragboard, read it and use it */
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                PlanEditorPane.this.getChildren().add(new Sphere(10));
                //target.setText(db.getString());
                success = true;
            }
    /* let the source know whether the string was successfully
     * transferred and used */
            event.setDropCompleted(success);

            event.consume();
        });
    }
}
