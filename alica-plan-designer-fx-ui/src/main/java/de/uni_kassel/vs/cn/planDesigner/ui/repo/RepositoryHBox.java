package de.uni_kassel.vs.cn.planDesigner.ui.repo;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.img.AlicaIcon;
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
    private Path pathToObject;

    public RepositoryHBox(T object, Path pathToObject) {
        this.object = object;
        this.pathToObject = pathToObject;

        getChildren().addAll(new ImageView(new AlicaIcon(object.getClass())),
                new Text(object.getName()));
        setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2) {
                MainController.getInstance().openFile(pathToObject.toFile());
            }
        });
    }

    public T getObject() {
        return object;
    }
}
