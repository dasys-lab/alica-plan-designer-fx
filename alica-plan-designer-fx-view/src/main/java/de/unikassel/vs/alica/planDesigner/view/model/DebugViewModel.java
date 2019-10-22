package de.unikassel.vs.alica.planDesigner.view.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DebugViewModel extends SerializableViewModel {
    private StringProperty etcPath;

    public DebugViewModel(long id, String name, String type, String etcPath) {
        super(id, name, type);
        this.etcPath = new SimpleStringProperty(etcPath);
    }
}
