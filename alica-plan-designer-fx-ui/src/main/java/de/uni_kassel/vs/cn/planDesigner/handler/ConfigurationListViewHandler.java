package de.uni_kassel.vs.cn.planDesigner.handler;

import de.uni_kassel.vs.cn.planDesigner.controller.ConfigurationWindowController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;

public class ConfigurationListViewHandler<T extends ListView.EditEvent<String>> implements EventHandler<Event>, ChangeListener<String> {

    private ConfigurationWindowController configWindowController;

    public ConfigurationListViewHandler(ConfigurationWindowController configWindowController) {
        this.configWindowController = configWindowController;
    }

    /**
     * determine type of event and call corresponding method
     *
     * @param event
     */
    public void handle(Event event) {
        if (event.getEventType() == ListView.editCommitEvent()) {
            handleEditCommit((ListView.EditEvent<String>) event);
        }
    }

    /**
     * Called when an element of the list view was changed
     *
     * @param event
     */
    public void handleEditCommit(ListView.EditEvent<String> event) {
        event.getSource().getItems().set(event.getIndex(), event.getNewValue());
        if (!event.getNewValue().isEmpty()) {
            if (event.getIndex() == event.getSource().getItems().size() - 1) {
                // last empty element was edited, so we need to add a new empty last element
                event.getSource().getItems().add("");
            }
        } else {
            if (event.getIndex() != event.getSource().getItems().size() - 1) {
                // another element than the last element was deleted (new value is empty), so remove this element
                event.getSource().getItems().remove(event.getIndex());
            }
        }
    }

    /**
     * Called when the selected element of the list view has changed
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (oldValue != null && !oldValue.isEmpty()) {
            System.out.println("Old: " + oldValue);
            configWindowController.storeWorkspace(oldValue);
        }
        if (newValue != null && !newValue.isEmpty()) {
            System.out.println("New: " + newValue);
            configWindowController.loadWorkspace(newValue);
        }
    }
}

