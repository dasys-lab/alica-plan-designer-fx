package de.unikassel.vs.alica.planDesigner.view.editor.tools;

import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ToolButton extends ToggleButton {

    public void setIcon(String type) {
        Image toolImage = new AlicaIcon(type, AlicaIcon.Size.SMALL);
        this.setGraphic(new ImageView(toolImage));
    }
}
