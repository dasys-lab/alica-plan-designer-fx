package de.uni_kassel.vs.cn.planDesigner.ui;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.ui.img.AlicaIcon;
import javafx.scene.image.ImageView;
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
    }
}
