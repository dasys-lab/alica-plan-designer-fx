package de.unikassel.vs.alica.planDesigner.view.model;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuantifierViewModel extends PlanElementViewModel{

    protected StringProperty quantifierType = new SimpleStringProperty(this, "quantifierType", "");
    protected LongProperty scope = new SimpleLongProperty(this, "scope", 0L);
    protected StringProperty sorts = new SimpleStringProperty(this, "sorts", "");

    public QuantifierViewModel(long id, String name, String quantifierType) {
        super(id, name, quantifierType);
    }

    public String getQuantifierType() {
        return quantifierType.get();
    }

    public void setQuantifierType(String quantifierType) {
        this.quantifierType.set(quantifierType);
    }

    public StringProperty quantifierTypeProperty() {
        return quantifierType;
    }


    public long getScope() {
        return scope.get();
    }

    public void setScope(long scope) {
        this.scope.set(scope);
    }

    public LongProperty scopeProperty() {
        return scope;
    }


    public String getSorts() {
        return sorts.get();
    }

//    public void setSorts(String sorts) {
//        this.sorts.set(sorts);
//    }

    public void setSorts(List<String> sorts) {
        this.sorts.set(String.join(" ", sorts));
    }

    public StringProperty sortsProperty() {
        return sorts;
    }

    @Override
    public void registerListener(IGuiModificationHandler handler) {
        super.registerListener(handler);
        quantifierType.addListener((observable, oldValue, newValue) -> {
            fireGUIAttributeChangeEvent(handler, newValue, quantifierType.getClass().getSimpleName(), quantifierType.getName());
        });
        scope.addListener((observable, oldValue, newValue) -> {
            fireGUIAttributeChangeEvent(handler, newValue, scope.getClass().getSimpleName(), scope.getName());
        });
        sorts.addListener((observable, oldValue, newValue) -> {
            List<String> newValues = new ArrayList<>(Arrays.asList(newValue.split(" ")));
            newValues.removeIf(String::isEmpty);
            fireGUIAttributeChangeEvent(handler, newValues, sorts.getClass().getSimpleName(), sorts.getName());
        });
    }
}
