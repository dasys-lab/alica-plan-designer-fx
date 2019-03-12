package de.unikassel.vs.alica.planDesigner.alicamodel;


import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class Quantifier extends PlanElement {

    protected final SimpleStringProperty quantifierType = new SimpleStringProperty(this, "quantifierType", "");

    protected ObjectProperty<PlanElement> scope = new SimpleObjectProperty<>(this, "scope", null);
    protected ObjectProperty<List<String>> sorts = new SimpleObjectProperty<>();

    public List<String> getSorts() {
        return sorts.get();
    }

    public void setSorts(List<String> sorts) {
        if(sorts == null){
            this.sorts.set(new ArrayList<>());
        }else {
            this.sorts.set(new ArrayList<>(sorts));
        }
    }

    public ObjectProperty<List<String>> sortsProperty() {
        return sorts;
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

    public void registerListenerToAbstractPlan(AbstractPlan abstractPlan) {
        InvalidationListener dirty = obs -> abstractPlan.setDirty(true);

        this.nameProperty().addListener(dirty);
        this.commentProperty().addListener(dirty);
        this.scopeProperty().addListener(dirty);
        this.quantifierTypeProperty().addListener(dirty);
        this.sortsProperty().addListener(dirty);
    }
}
