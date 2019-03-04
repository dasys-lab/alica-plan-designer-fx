package de.unikassel.vs.alica.planDesigner.alicamodel;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class Quantifier extends PlanElement {

    protected final SimpleStringProperty quantifierType = new SimpleStringProperty(this, "quantifierType", "");

    protected ObjectProperty<PlanElement> scope = new SimpleObjectProperty<>(this, "scope", null);
    protected List<String> sorts;

    public List<String> getSorts() {
        return sorts;
    }

    public void setSorts(List<String> sorts) {
        this.sorts = new ArrayList<>(sorts);
    }

    public PlanElement getScope() {
        return this.scope.getValue();
    }

    public void setScope(PlanElement scope) {
        this.scope.setValue(scope);
    }

    public ObjectProperty<PlanElement> scopeProperty() {
        return scope;
    }

    public String getQuantifierType() {
        return quantifierType.get();
    }

    public void setQuantifierType(String quantifierType) {
        this.quantifierType.set(quantifierType);
    }

    public SimpleStringProperty quantifierTypeProperty() {
        return quantifierType;
    }
}
