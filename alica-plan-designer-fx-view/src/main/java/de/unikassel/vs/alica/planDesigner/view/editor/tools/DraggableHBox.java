package de.unikassel.vs.alica.planDesigner.view.editor.tools;

import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.awt.*;

/**
 * GUI Container class for all [Icon,Text,Tool]-based entries.
 * Child classes are ToolHBox {@link EditorToolBar} and RepositoryHBox (for reusing plan elements from repo view).
 */
public class DraggableHBox extends HBox {
    protected ImageView icon;
    protected Text text;

    public DraggableHBox() {
        EventHandler<Event> eventHandler = new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                fireEvent(event);
            }
        };

        icon = new ImageView();
        icon.setOnDragDetected(eventHandler);
        icon.setOnDragDone(eventHandler);
        text = new Text();
        text.setOnDragDetected(eventHandler);
        text.setOnDragDone(eventHandler);
        this.setSpacing(5);
        getChildren().addAll(icon, text);
    }

    public void setIcon(String iconName) {
        this.icon.setImage(new AlicaIcon(iconName, AlicaIcon.Size.SMALL));
    }

    public void setText(String text) {
        this.text.setText(text);
    }
}
