package de.unikassel.vs.alica.planDesigner.view.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class QuantifierViewModel extends PlanElementViewModel{

    protected StringProperty quantifierType = new SimpleStringProperty();
    protected LongProperty scope = new SimpleLongProperty();
    protected StringProperty sorts = new SimpleStringProperty();

    public QuantifierViewModel(long id, String name, String quantifierType) {
        super(id, name, quantifierType);
    }

    public String getQuantifierType() {
        return quantifierType.get();
    }

    public void setQuantifierType(String quantifierType) {
        this.quantifierType.set(quantifierType);
    }

    public StringProperty quantifierType() {
        return quantifierType;
    }


    public long getScope() {
        return scope.get();
    }

    public void setScope(long scope) {
        this.scope.set(scope);
    }

    public LongProperty scope() {
        return scope;
    }


    public String getSorts() {
        return sorts.get();
    }

    public void setSorts(String sorts) {
        this.sorts.set(sorts);
    }

    public StringProperty sorts() {
        return sorts;
    }
}
