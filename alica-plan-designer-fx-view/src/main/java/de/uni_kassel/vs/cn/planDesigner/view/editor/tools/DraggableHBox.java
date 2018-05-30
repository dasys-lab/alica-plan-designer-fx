package de.uni_kassel.vs.cn.planDesigner.view.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.view.img.AlicaIcon;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * GUI Container class for all [Icon,Text,Tool]-based entries.
 * Child classes are ToolHBox {@link PLDToolBar} and RepositoryHBox (for reusing plan elements from repo view).
 */
public class DraggableHBox extends HBox {
    protected ImageView icon;
    protected Text text;

    public DraggableHBox() {
        icon = new ImageView();
        icon.setOnDragDetected(event -> {this.fireEvent(event);});
        icon.setOnDragDone(event -> {this.fireEvent(event);});
        text = new Text();
        text.setOnDragDetected(event -> {this.fireEvent(event);});
        text.setOnDragDone(event -> {this.fireEvent(event);});
        getChildren().addAll(icon, text);
    }

    public void setIcon(String iconName) {
        this.icon.setImage(new AlicaIcon(iconName));
    }

    public void setText(String text) {
        this.text.setText(text);
    }
}
