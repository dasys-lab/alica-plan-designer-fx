package de.uni_kassel.vs.cn.planDesigner.ui.editor;

import de.uni_kassel.vs.cn.planDesigner.common.DragableAlicaType;
import de.uni_kassel.vs.cn.planDesigner.ui.img.AlicaIcon;
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
    public DragableHBox(DragableAlicaType alicaType) {
        super();
        ImageView imageView = new ImageView(
                new AlicaIcon(alicaType.getAssociatedClass()));
        getChildren().addAll(imageView,
                new Text(alicaType.toString()));
        EventHandler<MouseEvent> onDragDetectedHandler = event -> {
    /* drag was detected, start a drag-and-drop gesture*/
    /* allow any transfer mode */
            Dragboard db = startDragAndDrop(TransferMode.ANY);

    /* Put a string on a dragboard */
            ClipboardContent content = new ClipboardContent();
            content.putString(alicaType.name());
            db.setContent(content);

            event.consume();
        };

        EventHandler<DragEvent> onDragDoneHandler = event -> {
    /* the drag and drop gesture ended */
    /* if the data was successfully moved, clear it */
            event.consume();
        };
        this.setOnDragDetected(onDragDetectedHandler);
        imageView.setOnDragDetected(onDragDetectedHandler);
        this.setOnDragDone(onDragDoneHandler);
        imageView.setOnDragDone(onDragDoneHandler);
    }
}
