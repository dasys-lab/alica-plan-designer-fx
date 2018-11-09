package de.unikassel.vs.alica.planDesigner.view.model;

import javafx.beans.property.SimpleBooleanProperty;

public class SerializableViewModel extends PlanElementViewModel {

    protected final SimpleBooleanProperty dirty = new SimpleBooleanProperty();

    public SerializableViewModel(long id, String name, String type) {
        super(id, name, type);
    }

    public boolean getDirty() {
        return dirty.get();
    }
    public void setDirty(boolean dirty) {
        this.dirty.set(dirty);
    }
    public SimpleBooleanProperty dirtyProperty() {
        return dirty;
    }
}
