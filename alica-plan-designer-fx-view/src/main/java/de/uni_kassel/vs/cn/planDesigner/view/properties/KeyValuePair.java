package de.uni_kassel.vs.cn.planDesigner.view.properties;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class KeyValuePair {
    private final StringProperty key = new SimpleStringProperty();
    private final StringProperty value = new SimpleStringProperty();
    private final boolean editable;

    public KeyValuePair(String key, String value, boolean editable) {
        setKey(key);
        setValue(value);
        this.editable = editable;
    }

    public boolean isEditable() {
        return editable;
    }

    public final StringProperty keyProperty() {
        return this.key;
    }
    public final String getKey() {
        return this.keyProperty().get();
    }
    public final void setKey(String key) {
        this.keyProperty().set(key);
    }

    public final StringProperty valueProperty() {
        return this.value;
    }
    public final String getValue() {
        return this.valueProperty().get();
    }
    public final void setValue(String value) {
        this.valueProperty().set(value);
    }
}