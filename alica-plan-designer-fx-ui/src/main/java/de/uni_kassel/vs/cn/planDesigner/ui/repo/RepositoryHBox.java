package de.uni_kassel.vs.cn.planDesigner.ui.repo;

import de.uni_kassel.vs.cn.planDesigner.alica.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.AbstractPlanTool;
import de.uni_kassel.vs.cn.planDesigner.ui.img.AlicaIcon;
import de.uni_kassel.vs.cn.planDesigner.ui.menu.ShowUsagesMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.nio.file.Path;

/**
 * Created by marci on 25.11.16.
 */
public class RepositoryHBox<T extends PlanElement> extends HBox {
    private T object;
    private AbstractPlanTool dragTool;

    public RepositoryHBox(T object, Path pathToObject, AbstractPlanTool dragTool) {
        this.object = object;
        this.dragTool = dragTool;
        setOnContextMenuRequested(e -> {
            ContextMenu contextMenu = new ContextMenu(new ShowUsagesMenuItem(object));
            contextMenu.show(RepositoryHBox.this, e.getScreenX(), e.getScreenY());
        });

        getChildren().addAll(new ImageView(new AlicaIcon(object.getClass())),
                new Text(object.getName()));
        setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2) {
                MainController.getInstance().openFile(pathToObject.toFile());
            }
        });

        if (dragTool != null) {
            initDragSupport();
        }
    }

    public T getObject() {
        return object;
    }

    protected void initDragSupport() {
        setOnDragDetected(e -> {
            startFullDrag();
            dragTool.setActiveElement((AbstractPlan) getObject());
            dragTool.setVisualRepresentation(this);
            dragTool.startPhase();
        });
    }


}
