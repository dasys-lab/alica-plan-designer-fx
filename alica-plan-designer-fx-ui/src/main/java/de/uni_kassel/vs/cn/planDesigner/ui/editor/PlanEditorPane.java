package de.uni_kassel.vs.cn.planDesigner.ui.editor;

import javafx.geometry.Insets;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;

/**
 * Created by marci on 23.11.16.
 */
public class PlanEditorPane extends AnchorPane {
    public PlanEditorPane() {
        super();
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
