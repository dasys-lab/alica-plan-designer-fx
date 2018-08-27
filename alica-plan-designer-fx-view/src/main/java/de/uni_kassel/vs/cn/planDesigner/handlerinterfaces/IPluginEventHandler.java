package de.uni_kassel.vs.cn.planDesigner.handlerinterfaces;

import javafx.beans.value.ChangeListener;

public interface IPluginEventHandler extends ChangeListener<String> {
    public void updateAvailablePlugins();
}
