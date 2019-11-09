package de.unikassel.vs.alica.planDesigner.plugin;

import de.unikassel.vs.alica.codegen.plugin.PluginManager;
import de.unikassel.vs.alica.planDesigner.controller.ConfigurationWindowController;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IPluginEventHandler;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.List;

public class PluginEventHandler implements IPluginEventHandler {

    private PluginManager pluginManager;
    private ConfigurationWindowController configWindowController;

    public PluginEventHandler(ConfigurationWindowController configWindowController, PluginManager pluginManager) {
        this.configWindowController = configWindowController;
        this.pluginManager = pluginManager;
    }

    @Override
    public void updateAvailablePlugins() {
        pluginManager.updateAvailablePlugins(configWindowController.getPluginsFolder());
        configWindowController.setAvailablePlugins(pluginManager.getAvailablePluginNames());
    }

    @Override
    public List<String> getAvailablePlugins() {
        return pluginManager.getAvailablePluginNames();
    }

    @Override
    public Parent getPluginUI(String pluginName) throws IOException {
        return pluginManager.getPlugin(pluginName).getPluginUI();
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        pluginManager.setDefaultPlugin(newValue);
    }
}