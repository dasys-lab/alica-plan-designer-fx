package de.unikassel.vs.alica.planDesigner.alicamodel;

public class Transition extends  PlanElement{
    public static final String INSTATE = "inState";
    public static final String OUTSTATE = "outState";

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
