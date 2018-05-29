package de.uni_kassel.vs.cn.planDesigner.view.repo;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.AbstractPlanTool;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.DragableHBox;
import de.uni_kassel.vs.cn.planDesigner.view.img.AlicaIcon;
import de.uni_kassel.vs.cn.planDesigner.view.menu.ShowUsagesMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;

import java.nio.file.Path;

/**
 * Created by marci on 25.11.16.
 */
public class RepositoryHBox<T extends PlanElement> extends DragableHBox {
    private T object;
    private AbstractPlanTool dragTool;

    public RepositoryHBox(T object, Path pathToObject, AbstractPlanTool dragTool) {
        super(dragTool.createNewObject(), dragTool);
        dragTool.setDragableHBox(this);
        this.object = object;
        this.dragTool = dragTool;

        init(object, pathToObject);

        if (dragTool instanceof RepositoryTabPane.TaskTool == false) {
            initDragSupport();
        }
    }

    public RepositoryHBox(T object, Path pathToObject) {
        super(object, new AbstractPlanTool(null));
        this.object = object;
        init(object, pathToObject);
    }

    private void init(T object, Path pathToObject) {
        setOnContextMenuRequested(e -> {
            ContextMenu contextMenu = new ContextMenu(new ShowUsagesMenuItem(object));
            contextMenu.show(RepositoryHBox.this, e.getScreenX(), e.getScreenY());
        });

        if(object instanceof Plan && ((Plan)object).isMasterPlan()) {

            getChildren().addAll(new ImageView(new AlicaIcon("masterplan")),
                    new Text(" " + object.getName()));

        } else {

            getChildren().addAll(new ImageView(new AlicaIcon(object.getClass().getSimpleName())),
                    new Text(" " + object.getName()));
        }
        setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2) {
                MainController.getInstance().openFile(pathToObject.toFile());
            }
        });
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

    @Override
    public void addContents(ImageView imageView, String className) {

    }
}
