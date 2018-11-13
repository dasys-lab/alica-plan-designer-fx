package de.unikassel.vs.alica.planDesigner.view.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VariableViewModel extends PlanElementViewModel {

    protected final StringProperty variableType = new SimpleStringProperty();

    public VariableViewModel(long id, String name, String elementType) {
        super(id, name, elementType);
    }

    public final StringProperty variableTypeProperty() {
        return this.variableType;
    }
    public final String getVariableType() {
        return this.variableType.get();
    }
    public final void setVariableType(String type) {
        this.variableType.set(type);
    }

}
