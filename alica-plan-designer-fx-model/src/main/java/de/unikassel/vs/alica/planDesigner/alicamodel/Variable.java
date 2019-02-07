package de.unikassel.vs.alica.planDesigner.alicamodel;

import javafx.beans.property.SimpleStringProperty;

public class Variable extends PlanElement {

    protected final SimpleStringProperty variableType = new SimpleStringProperty();

    public String getVariableType() { return variableType.get(); }
    public void setVariableType(String variableType) { this.variableType.set(variableType); }
    public SimpleStringProperty variableTypeProperty() {
        return variableType;
    }
}
