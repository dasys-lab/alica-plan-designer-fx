package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class SerializablePlanElement extends PlanElement {

    @JsonIgnore
    protected final SimpleBooleanProperty dirty = new SimpleBooleanProperty();
    protected final SimpleStringProperty relativeDirectory = new SimpleStringProperty();

    public boolean getDirty() {
        return dirty.get();
    }
    public void setDirty(boolean dirty) {
        this.dirty.set(dirty);
    }
    public SimpleBooleanProperty dirtyProperty() {
        return dirty;
    }

    public String getRelativeDirectory() {
        return relativeDirectory.get();
    }
    public void setRelativeDirectory(String relativeDirectory) {
        this.relativeDirectory.set(relativeDirectory);
    }
    public SimpleStringProperty relativeDirectoryProperty() {
        return relativeDirectory;
    }
}
