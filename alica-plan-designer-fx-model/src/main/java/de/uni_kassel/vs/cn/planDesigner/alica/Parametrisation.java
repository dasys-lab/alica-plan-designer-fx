package de.uni_kassel.vs.cn.planDesigner.alica;

public class Parametrisation extends PlanElement{
    protected AbstractPlan subPlan;
    protected Variable subVariable;
    protected Variable variable;

    public AbstractPlan getSubPlan() {
        return subPlan;
    }

    public void setSubPlan(AbstractPlan subPlan) {
        this.subPlan = subPlan;
    }

    public Variable getSubVariable() {
        return subVariable;
    }

    public void setSubVariable(Variable subVariable) {
        this.subVariable = subVariable;
    }

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }
}
