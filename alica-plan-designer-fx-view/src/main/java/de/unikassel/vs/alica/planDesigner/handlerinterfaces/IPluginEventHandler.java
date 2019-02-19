package de.unikassel.vs.alica.planDesigner.handlerinterfaces;

import javafx.beans.value.ChangeListener;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.List;

public interface IPluginEventHandler extends ChangeListener<String> {
    void updateAvailablePlugins();
    List<String> getAvailablePlugins();
    Parent getPluginUI(String pluginName) throws IOException;
}
