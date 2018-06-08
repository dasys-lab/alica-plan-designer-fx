package de.uni_kassel.vs.cn.planDesigner.configuration;

import de.uni_kassel.vs.cn.planDesigner.controller.ConfigurationWindowController;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IConfigurationEventHandler;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ConfigurationEventHandler implements IConfigurationEventHandler<ListView.EditEvent<String>> {

    private static final Logger LOG = LogManager.getLogger(ConfigurationEventHandler.class);
    private ConfigurationWindowController configWindowController;
    private ConfigurationManager configManager;

    public ConfigurationEventHandler(ConfigurationWindowController configWindowController, ConfigurationManager configManager) {
        this.configWindowController = configWindowController;
        this.configManager = configManager;
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
                configManager.addConfiguration(event.getNewValue());
                event.getSource().getItems().add("");
            } else {
                // another element than the last element was edited, rename
                configManager.renameConfiguration(event.getSource().getItems().get(event.getIndex()), event.getNewValue());
            }
            event.getSource().getItems().set(event.getIndex(), event.getNewValue());
        } else {
            if (event.getIndex() != event.getSource().getItems().size() - 1) {
                // another element than the last element was deleted (new value is empty), so remove this element
                if (!configManager.removeConfiguration(event.getSource().getItems().get(event.getIndex()))) {
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
            storeConfiguration(oldValue);
        }
        showSelectedConfiguration();
    }

    protected  void storeConfiguration(String confName) {
        Configuration conf = configManager.getConfiguration(confName);
        if (conf == null) {
            return;
        }

        conf.setPlansPath(configWindowController.getPlansFolder());
        conf.setRolesPath(configWindowController.getRolesFolder());
        conf.setTasksPath(configWindowController.getTasksFolder());
        conf.setGenSrcPath(configWindowController.getSourceFolder());
        conf.setPluginsPath(configWindowController.getPluginsFolder());
        conf.setDefaultPluginName(configWindowController.getDefaultPluginName());
    }

    public void showSelectedConfiguration() {
        String selectedConfName = configWindowController.getSelectedConfName();
        if (selectedConfName == null || selectedConfName.isEmpty()) {
            return;
        }

        configManager.setActiveConfiguration(selectedConfName);
        Configuration conf = configManager.getConfiguration(selectedConfName);
        if (conf == null) {
            return;
        }

        configWindowController.setPlansFolder(conf.getPlansPath());
        configWindowController.setRolesFolder(conf.getRolesPath());
        configWindowController.setTasksFolder(conf.getTasksPath());
        configWindowController.setPluginsFolder(conf.getPluginsPath());
        configWindowController.setSourceFolder(conf.getGenSrcPath());
    }

    public void updateAvailableConfigurations() {
        configWindowController.setAvailableConfigs(configManager.getConfigurationNames());
    }

    @Override
    public void updateExternalTools() {
        configWindowController.setClangFormat(configManager.getClangFormatPath());
        configWindowController.setSourceCodeEditor(configManager.getEditorExecutablePath());
    }

    @Override
    public void save(String confName) {
        storeConfiguration(confName);
        configManager.writeToDisk();
    }

    @Override
    public void setEditorExecutablePath(String editorExecutablePath) {
        configManager.setEditorExecutablePath(editorExecutablePath);
    }

    @Override
    public void setClangFormatPath(String clangFormatPath) {
        configManager.setClangFormatPath(clangFormatPath);
    }
}

