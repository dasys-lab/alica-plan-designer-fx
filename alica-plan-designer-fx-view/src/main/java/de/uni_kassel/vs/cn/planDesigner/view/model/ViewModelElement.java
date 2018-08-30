package de.uni_kassel.vs.cn.planDesigner.view.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ViewModelElement {
    protected SimpleLongProperty id;
    protected SimpleStringProperty name;
    protected SimpleStringProperty type;
    protected SimpleStringProperty relativeDirectory;
    protected SimpleLongProperty parentId;

    public ViewModelElement(long id, String name, String type) {
        this.id = new SimpleLongProperty();
        this.name = new SimpleStringProperty();
        this.type = new SimpleStringProperty();
        this.relativeDirectory = new SimpleStringProperty();
        this.parentId = new SimpleLongProperty();

        this.id.setValue(id);
        this.name.setValue(name);
        this.type.setValue(type);
    }

    public ViewModelElement(long id, String name, String type, String relativeDirectory) {
        this(id, name, type);
        this.relativeDirectory.setValue(relativeDirectory);
    }

    public final LongProperty idProperty() {
        return this.id;
    }
    public long getId() {
        return this.id.get();
    }

    public final StringProperty nameProperty() {
        return this.name;
    }
    public String getName() {
        return name.get();
    }
    public void setName(String name) {
        this.name.set(name);
    }

    public String getType() {
        return type.get();
    }

    public String getRelativeDirectory() {
        return relativeDirectory.get();
    }

    public void setRelativeDirectory(String relativeDirectory) {this.relativeDirectory.setValue(relativeDirectory);}

    public void setParentId(long id) {
        this.parentId.setValue(id);
    }

    public long getParentId() {
        return parentId.get();
    }

    public String toString() {
        return type + ": " + name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ViewModelElement) {
            ViewModelElement otherElement = (ViewModelElement) other;
            return this.getId() == otherElement.getId() && this.getName().equals(otherElement.getName());
        } else {
            return false;
        }
    }
}
