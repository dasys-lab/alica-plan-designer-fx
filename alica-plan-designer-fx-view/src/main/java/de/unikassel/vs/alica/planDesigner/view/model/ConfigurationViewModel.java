package de.unikassel.vs.alica.planDesigner.view.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class ConfigurationViewModel extends SerializableViewModel {

    protected ObjectProperty<BehaviourViewModel> behaviour;
    protected ObservableMap<String, String> keyValuePairs;

    public ConfigurationViewModel(long id, String name, String type) {
        super(id, name, type);
        this.behaviour = new SimpleObjectProperty<>();
        this.keyValuePairs = FXCollections.observableHashMap();
    }

    public ObjectProperty<BehaviourViewModel> behaviourProperty() {
        return behaviour;
    }

    public BehaviourViewModel getBehaviour() {
        return behaviour.get();
    }

    public void setBehaviour(BehaviourViewModel behaviour) {
        this.behaviour.set(behaviour);
    }

    public ObservableMap<String, String> getKeyValuePairs() {
        return keyValuePairs;
    }
}
