package de.uni_kassel.vs.cn.planDesigner.ui.editor;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.alica.SuccessState;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import javafx.geometry.Insets;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marci on 23.11.16.
 */
public class PlanEditorPane extends AnchorPane {

    private PlanModelVisualisationObject planModelVisualisationObject;

    public PlanEditorPane(PlanModelVisualisationObject planModelVisualisationObject) {
        super();
        this.planModelVisualisationObject = planModelVisualisationObject;
        addDragDropSupport();
        visualize();
    }

    private void visualize() {
        List<Circle> collect = planModelVisualisationObject
                .getPlan()
                .getStates()
                .stream()
                .map(e -> {
                    Circle sphere = new Circle(20);
                    PmlUiExtension pmlUiExtension = planModelVisualisationObject
                            .getPmlUiExtensionMap()
                            .getExtension()
                            .stream()
                            .filter(f -> {
                                String s = f.getKey().toString().split("#")[1];
                                return Long.valueOf(s.substring(0, s.lastIndexOf(')'))) == e.getId();
                            })
                            .findFirst().get().getValue();
                    if (e instanceof SuccessState) {
                        sphere.setFill(Color.GREEN);
                    }
                    sphere.setLayoutX(pmlUiExtension.getXPos() * 1.8);
                    sphere.setLayoutY(pmlUiExtension.getYPos() * 1.8);
                    return sphere;
                })
                .collect(Collectors.toList());
        getChildren().addAll(collect);
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
