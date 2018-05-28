package de.uni_kassel.vs.cn.planDesigner.alica;

public class Transition extends  PlanElement{
    protected State inState;
    protected State outState;
    protected PreCondition preCondition;
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
