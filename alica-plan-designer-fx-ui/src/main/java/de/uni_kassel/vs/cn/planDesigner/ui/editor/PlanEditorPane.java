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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        getChildren().addAll(stateContainers);
        getChildren().addAll(transitionContainers);
    }

    private List<TransitionContainer> createTransitionContainers() {
        List<TransitionContainer> transitions = new ArrayList<>();
        for (Transition transition : planModelVisualisationObject.getPlan().getTransitions()) {

            StateContainer fromState = stateContainers.stream()
                    .filter(e -> e.getState().equals(transition.getInState()))
                    .findFirst()
                    .orElse(null);

            StateContainer toState = stateContainers.stream()
                    .filter(e -> e.getState().equals(transition.getOutState()))
                    .findFirst()
                    .orElse(null);

            TransitionContainer transitionContainer = new TransitionContainer(transition, null, fromState, toState);
            transitions.add(transitionContainer);
        }
        return  transitions;
    }

    private List<StateContainer> createStateContainers() {
        return planModelVisualisationObject
                    .getPlan()
                    .getStates()
                    .stream()
                    .map(e -> {
                        PmlUiExtension pmlUiExtension = null;
                        for (Map.Entry<EObject, PmlUiExtension> entry : planModelVisualisationObject.getPmlUiExtensionMap().getExtension()) {
                            EObject temp = null;
                            if(entry.getKey().eIsProxy()) {
                                temp = EcoreUtil.resolve(entry.getKey(), planModelVisualisationObject.getPmlUiExtensionMap());
                            }

                            EObject expressionVar = entry.getKey();
                            if(temp != null) {
                                expressionVar = temp;
                            }
                            if (expressionVar instanceof State && ((State)expressionVar).getId() == e.getId()) {
                                pmlUiExtension = entry.getValue();
                                break;
                            }
                        }
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
