package de.unikassel.vs.alica.planDesigner.view.model;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VariableViewModel extends PlanElementViewModel {

    protected final StringProperty variableType = new SimpleStringProperty(null, "variableType", "");

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

    public void registerListener(IGuiModificationHandler handler) {
        super.registerListener(handler);
        variableType.addListener((observable, oldValue, newValue) -> {
            fireGUIAttributeChangeEvent(handler, newValue, variableType.getClass().getSimpleName(), variableType.getName());
        });
    }

}
