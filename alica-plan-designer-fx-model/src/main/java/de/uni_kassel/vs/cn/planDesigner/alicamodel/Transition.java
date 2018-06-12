package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.serialization.CustomPlanElementSerializer;

public class Transition extends  PlanElement{
    @JsonSerialize(using = CustomPlanElementSerializer.class)
    protected State inState;
    @JsonSerialize(using = CustomPlanElementSerializer.class)
    protected State outState;
    protected PreCondition preCondition;
    @JsonSerialize(using = CustomPlanElementSerializer.class)
    protected Synchronization synchronization;

    public State getInState() {
        return inState;
    }

    public void setInState(State inState) {
        this.inState = inState;
    }

    public State getOutState() {
        return outState;
    }

    public void setOutState(State outState) {
        this.outState = outState;
    }

    public Synchronization getSynchronization() {
        return synchronization;
    }

    public void setSynchronization(Synchronization synchronization) {
        this.synchronization = synchronization;
    }

    public PreCondition getPreCondition() {
        return preCondition;
    }

    public void setPreCondition(PreCondition preCondition) {
        this.preCondition = preCondition;
    }
}
