package de.unikassel.vs.alica.planDesigner.alicamodel;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Configuration extends AbstractPlan{
    private final ObjectProperty<Behaviour> behaviour = new SimpleObjectProperty<>();
    private final Map<String, String> keyValuePairs = new HashMap<>();

    public ObjectProperty<Behaviour> behaviourProperty() {
        return behaviour;
    }

    public Behaviour getBehaviour() {
        return behaviour.get();
    }

    public void setBehaviour(Behaviour behaviour){
        this.behaviour.set(behaviour);
    }

    public Map<String, String> getKeyValuePairs() {
        return Collections.unmodifiableMap(keyValuePairs);
    }

    public String putKeyValuePair(String key, String value) {
        getBehaviour().setDirty(true);
        return keyValuePairs.put(key, value);
    }

    public String removeKeyValuePair(String key) {
        getBehaviour().setDirty(true);
        return keyValuePairs.remove(key);
    }

    @Override
    public void registerDirtyFlag() {
        InvalidationListener dirty = obs -> getBehaviour().setDirty(true);

        this.nameProperty().addListener(dirty);
        this.commentProperty().addListener(dirty);
    }
}
