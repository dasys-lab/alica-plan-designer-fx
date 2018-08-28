package de.uni_kassel.vs.cn.planDesigner.plugin;

import de.uni_kassel.vs.cn.generator.plugin.PluginManager;
import de.uni_kassel.vs.cn.planDesigner.controller.ConfigurationWindowController;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IPluginEventHandler;
import javafx.beans.value.ObservableValue;

public class PluginEventHandler implements IPluginEventHandler {

    private PluginManager pluginManager;
    private ConfigurationWindowController configWindowController;

    public PluginEventHandler(ConfigurationWindowController configWindowController, PluginManager pluginManager) {
        this.configWindowController = configWindowController;
        this.pluginManager = pluginManager;
    }

    @Override
    public void updateAvailablePlugins() {
        configWindowController.setAvailablePlugins(pluginManager.getAvailablePluginNames());
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        pluginManager.setDefaultPlugin(newValue);
    }
}