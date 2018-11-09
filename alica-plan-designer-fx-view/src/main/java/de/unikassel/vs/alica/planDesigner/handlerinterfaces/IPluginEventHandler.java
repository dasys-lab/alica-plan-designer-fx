package de.unikassel.vs.alica.planDesigner.handlerinterfaces;

import javafx.beans.value.ChangeListener;

public interface IPluginEventHandler extends ChangeListener<String> {
    public void updateAvailablePlugins();
}
