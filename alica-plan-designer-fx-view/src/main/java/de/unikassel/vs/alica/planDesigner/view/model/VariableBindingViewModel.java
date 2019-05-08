package de.unikassel.vs.alica.planDesigner.view.model;

import javafx.beans.property.SimpleObjectProperty;

import java.util.Arrays;

public class VariableBindingViewModel extends PlanElementViewModel {

    protected final SimpleObjectProperty<AbstractPlanViewModel> subPlan = new SimpleObjectProperty<>(this, "subPlan", null);
    protected final SimpleObjectProperty<VariableViewModel> subVariable = new SimpleObjectProperty<>(this, "subVariable", null);
    protected final SimpleObjectProperty<VariableViewModel> variable = new SimpleObjectProperty<>(this, "variable", null);

    public VariableBindingViewModel(long id, String name, String type) {
        super(id, name, type);

        this.uiPropertyList.clear();
        this.uiPropertyList.addAll(Arrays.asList("name", "id", "comment", "relativeDirectory"));
    }

    public AbstractPlanViewModel getSubPlan() {
        return subPlan.get();
    }

    public void setSubPlan(AbstractPlanViewModel subPlan) {
        this.subPlan.set(subPlan);
    }

    public VariableViewModel getSubVariable() {
        return subVariable.get();
    }

    public void setSubVariable(VariableViewModel subVariable) {
        this.subVariable.set(subVariable);
    }

    public VariableViewModel getVariable() {
        return variable.get();
    }

    public void setVariable(VariableViewModel variable) {
        this.variable.set(variable);
    }
}
