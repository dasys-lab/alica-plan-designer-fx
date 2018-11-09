package de.unikassel.vs.alica.planDesigner.alicamodel;


import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Quantifier extends PlanElement {

    protected final SimpleStringProperty quantifierType = new SimpleStringProperty();

    protected PlanElement scope;
    protected ArrayList<String> sorts;


    public ArrayList<String> getSorts() {
        return sorts;
    }

    public PlanElement getScope() {
        return scope;
    }

    public void setScope(PlanElement scope) {
        this.scope = scope;
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
