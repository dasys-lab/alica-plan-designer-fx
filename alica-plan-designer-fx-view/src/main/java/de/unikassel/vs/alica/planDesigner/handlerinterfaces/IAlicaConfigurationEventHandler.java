package de.unikassel.vs.alica.planDesigner.handlerinterfaces;

import de.unikassel.vs.alica.planDesigner.view.model.AlicaConfigurationViewModel;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;

import java.io.IOException;

public interface IAlicaConfigurationEventHandler <T extends ListView.EditEvent<String>> extends EventHandler<Event>, ChangeListener<String> {
    AlicaConfigurationViewModel setDefaultConfiguration(AlicaConfigurationViewModel alicaConfigurationViewModel);

    void updateModel(AlicaConfigurationViewModel alicaConfigurationViewModel);

    boolean save(AlicaConfigurationViewModel alicaConfigurationViewModel);

    String loadAlicaConf(String confPath) throws IOException;
}
