package de.unikassel.vs.alica.planDesigner.view.editor.tools;

import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * GUI Container class for all [Icon,Text,Tool]-based entries.
 * Child classes are ToolHBox {@link EditorToolBar} and RepositoryLabel (for reusing plan elements from repo view).
 */
public class DraggableLabel extends Label {
    protected ImageView icon;

    public DraggableLabel() {
        EventHandler<Event> eventHandler = new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                fireEvent(event);
            }
        };

        this.setOnDragDetected(eventHandler);
        this.setOnDragDone(eventHandler);

        icon = new ImageView();
        this.setGraphic(icon);
    }

    public void setIcon(String iconName) {
        this.icon.setImage(new AlicaIcon(iconName, AlicaIcon.Size.SMALL));
    }
}
