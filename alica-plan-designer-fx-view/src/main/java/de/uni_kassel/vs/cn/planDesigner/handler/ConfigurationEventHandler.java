package de.uni_kassel.vs.cn.planDesigner.handler;

import de.uni_kassel.vs.cn.planDesigner.controller.ConfigurationWindowController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ConfigurationEventHandler<T extends ListView.EditEvent<String>> implements EventHandler<Event>, ChangeListener<String> {

    private static final Logger LOG = LogManager.getLogger(ConfigurationEventHandler.class);
    private ConfigurationWindowController configWindowController;

    public ConfigurationEventHandler(ConfigurationWindowController configWindowController) {
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
        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            handleTextFieldKeyReleased((KeyEvent) event);
        }
    }

    public void handleTextFieldKeyReleased(KeyEvent event) {
        configWindowController.setExternalToolValue((TextField) event.getSource());
        event.consume();
    }

    /**
     * Called when an element of the list view was changed
     *
     * @param event
     */
    public void handleEditCommit(ListView.EditEvent<String> event) {
        if (event.getNewValue().contains(","))
        {
            LOG.info("Comma character (,) is not allowed in configuration names! Value: " + event.getNewValue());
            return;
        }
        if (!event.getNewValue().isEmpty()) {
            if (event.getIndex() == event.getSource().getItems().size() - 1) {
                // last empty element was edited, so we need to add a new empty last element
                configWindowController.addConfiguration(event.getNewValue());
                event.getSource().getItems().add("");
            } else {
                // another element than the last element was edited, rename
                configWindowController.renameConfiguration(event.getSource().getItems().get(event.getIndex()), event.getNewValue());
            }
            event.getSource().getItems().set(event.getIndex(), event.getNewValue());
        } else {
            if (event.getIndex() != event.getSource().getItems().size() - 1) {
                // another element than the last element was deleted (new value is empty), so remove this element
                if (!configWindowController.removeConfiguration(event.getSource().getItems().get(event.getIndex()))) {
                    LOG.error("Unable to remove configuration " + event.getSource().getItems().get(event.getIndex()));
                }
                event.getSource().getItems().remove(event.getIndex());
            }
        }
        event.consume();
    }

    /**
     * Called when the selected element of the list view has changed.
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (oldValue != null && !oldValue.isEmpty()) {
            configWindowController.storeConfiguration(oldValue);
        }
        configWindowController.showSelectedConfiguration();
    }
}

