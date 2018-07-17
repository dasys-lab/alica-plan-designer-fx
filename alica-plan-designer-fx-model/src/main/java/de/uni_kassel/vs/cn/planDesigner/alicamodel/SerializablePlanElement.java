package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import javafx.beans.property.SimpleStringProperty;

public class SerializablePlanElement extends PlanElement {

    protected final SimpleStringProperty relativeDirectory = new SimpleStringProperty();

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
