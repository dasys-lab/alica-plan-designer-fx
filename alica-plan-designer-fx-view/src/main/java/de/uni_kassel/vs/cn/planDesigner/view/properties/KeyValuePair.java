package de.uni_kassel.vs.cn.planDesigner.view.properties;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class KeyValuePair {
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty value = new SimpleStringProperty();
    private final boolean editable;

    public KeyValuePair(String name, String value, boolean editable) {
        setName(name);
        setValue(value);
        this.editable = editable;
    }

    public boolean isEditable() {
        return editable;
    }

    public final StringProperty keyProperty() {
        return this.name;
    }
    public final String getName() {
        return this.keyProperty().get();
    }
    public final void setName(String name) {
        this.keyProperty().set(name);
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