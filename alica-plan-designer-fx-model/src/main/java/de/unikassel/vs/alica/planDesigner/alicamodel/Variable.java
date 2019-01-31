package de.unikassel.vs.alica.planDesigner.alicamodel;

import javafx.beans.property.SimpleStringProperty;

public class Variable extends PlanElement {

    protected final SimpleStringProperty type = new SimpleStringProperty();

    public String getType() { return type.get(); }
    public void setType(String type) { this.type.set(type); }
    public SimpleStringProperty typeProperty() {
        return type;
    }
}
