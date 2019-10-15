package de.unikassel.vs.alica.planDesigner.handlerinterfaces;

import de.unikassel.vs.alica.planDesigner.events.ConfigEvent;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;

import java.util.HashMap;

public interface IConfigurationEventHandler<T extends ListView.EditEvent<String>> extends EventHandler<Event>, ChangeListener<String> {
    public void save(String confName);
    public void delete(String confName);
    public void setEditorExecutablePath(String editorExecutablePath);
    public void setClangFormatPath(String clangFormatPath);
    public void showSelectedConfiguration();
    public void updateAvailableConfigurations();
    public void updateExternalTools();
    public void selectConfiguration(String confName);
    public void handlePreferredWindowSettings(ConfigEvent event);
    public HashMap<String, Double> getPreferredWindowSettings();
}
