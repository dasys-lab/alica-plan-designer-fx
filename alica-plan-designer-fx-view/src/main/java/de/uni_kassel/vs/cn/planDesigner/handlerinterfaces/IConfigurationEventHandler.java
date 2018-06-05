package de.uni_kassel.vs.cn.planDesigner.handlerinterfaces;

import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;

public interface IConfigurationEventHandler<T extends ListView.EditEvent<String>> extends EventHandler<Event>, ChangeListener<String> {
    public void save(String confName);
    public void setEditorExecutablePath(String editorExecutablePath);
    public void setClangFormatPath(String clangFormatPath);
    public void showSelectedConfiguration();
}
