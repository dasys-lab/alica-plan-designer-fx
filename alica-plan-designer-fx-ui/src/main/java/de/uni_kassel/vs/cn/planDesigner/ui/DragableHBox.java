package de.uni_kassel.vs.cn.planDesigner.ui;

import de.uni_kassel.vs.cn.planDesigner.common.DragableAlicaType;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Created by marci on 23.11.16.
 */
public class DragableHBox extends HBox {
    public DragableHBox(String resourceName, DragableAlicaType alicaType) {
        super();
        getChildren().addAll(new ImageView(
                new Image(this.getClass().getClassLoader().getResourceAsStream("images/" + resourceName + "16x16.png"))),
                new Text(alicaType.toString()));
        this.setOnDragDetected(event -> {
    /* drag was detected, start a drag-and-drop gesture*/
    /* allow any transfer mode */
            Dragboard db = startDragAndDrop(TransferMode.ANY);

    /* Put a string on a dragboard */
            ClipboardContent content = new ClipboardContent();
            content.putString(alicaType.name());
            db.setContent(content);

            event.consume();
        });

        this.setOnDragDone(event -> {
    /* the drag and drop gesture ended */
    /* if the data was successfully moved, clear it */
            event.consume();
        });
    }
}
