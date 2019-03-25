package de.unikassel.vs.alica.planDesigner.view.model;

public class ParametrizationViewModel extends PlanElementViewModel {

    protected PlanViewModel subPlan;
    protected VariableViewModel subVariable;
    protected VariableViewModel variable;

    public ParametrizationViewModel(long id, String name, String type) {
        super(id, name, type);
    }

    public PlanViewModel getSubPlan() {
        return subPlan;
    }

    public void setSubPlan(PlanViewModel subPlan) {
        this.subPlan = subPlan;
    }

    public VariableViewModel getSubVariable() {
        return subVariable;
    }

    public void setSubVariable(VariableViewModel subVariable) {
        this.subVariable = subVariable;
    }

    public VariableViewModel getVariable() {
        return variable;
    }

    public void setVariable(VariableViewModel variable) {
        this.variable = variable;
    }
}
