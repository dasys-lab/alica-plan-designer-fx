package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.ui.img.AlicaIcon;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Created by marci on 23.11.16.
 */
public class DragableHBox<T extends PlanElement> extends HBox {
    private Tool<T> controller;

    public DragableHBox(T alicaType, Tool<T> controller) {
        this.controller = controller;
        Class<?> superclass = alicaType.getClass().getSuperclass();
        ImageView imageView = new ImageView(
                new AlicaIcon(superclass));
        String superClassName = superclass.getSimpleName().toLowerCase().toString();
        getChildren().addAll(imageView,
                new Text(superClassName));
        EventHandler<MouseEvent> onDragDetectedHandler = event -> {
    /* drag was detected, start a drag-and-drop gesture*/
    /* allow any transfer mode */
            Dragboard db = startDragAndDrop(TransferMode.ANY);

    /* Put a string on a dragboard */
            ClipboardContent content = new ClipboardContent();
            content.putString(superClassName);
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
