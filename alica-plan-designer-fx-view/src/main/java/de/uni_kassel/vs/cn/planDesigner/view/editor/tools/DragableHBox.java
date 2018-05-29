package de.uni_kassel.vs.cn.planDesigner.view.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.view.img.AlicaIcon;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Created by marci on 23.11.16.
 */
public class DragableHBox<T extends PlanElement> extends HBox {
    private AbstractTool<T> controller;

    public DragableHBox(T alicaType, AbstractTool<T> controller) {
        this.controller = controller;
        Class<?> alicaTypeClass = alicaType.getClass();
        ImageView imageView = new ImageView(new AlicaIcon(alicaTypeClass.getSimpleName()));
        String className = alicaTypeClass.getSimpleName().replace("Impl", "");
        addContents(imageView, className);

        if (controller instanceof AbstractPlanTool == false) {
            EventHandler<MouseEvent> onDragDetectedHandler = event -> {
                startFullDrag();
                this.controller.startPhase();
                event.consume();
            };

            EventHandler<DragEvent> onDragDoneHandler = event -> {
                event.consume();
            };
            this.setOnDragDetected(onDragDetectedHandler);
            imageView.setOnDragDetected(onDragDetectedHandler);
            this.setOnDragDone(onDragDoneHandler);
            imageView.setOnDragDone(onDragDoneHandler);
        }
    }

    public void addContents(ImageView imageView, String className) {
        getChildren().addAll(imageView, new Text(className));
    }
}
