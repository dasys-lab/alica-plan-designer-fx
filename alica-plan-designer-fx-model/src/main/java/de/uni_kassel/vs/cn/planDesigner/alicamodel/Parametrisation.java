package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.serialization.CustomFileReferenceSerializer;

public class Parametrisation extends PlanElement{
    @JsonSerialize(using = CustomFileReferenceSerializer.class)
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
