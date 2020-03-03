package de.unikassel.vs.alica.planDesigner.handlerinterfaces;

import de.unikassel.vs.alica.planDesigner.view.model.GlobalsConfViewModel;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;

public interface IGlobalsConfEventHandler  <T extends ListView.EditEvent<String>> extends EventHandler<Event>, ChangeListener<String> {
    GlobalsConfViewModel setDefaultConfiguration(GlobalsConfViewModel globalsConfViewModel);

    String loadAlicaConf(String confPath);

    void updateModel(GlobalsConfViewModel globalsConfViewModel);

    boolean save(GlobalsConfViewModel globalsConfViewModel);
}
