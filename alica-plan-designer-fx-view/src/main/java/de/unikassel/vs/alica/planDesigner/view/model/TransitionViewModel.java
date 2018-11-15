package de.unikassel.vs.alica.planDesigner.view.model;

public class TransitionViewModel extends PlanElementViewModel {

    public static final String INSTATE = "inState";
    public static final String OUTSTATE = "outState";

    protected StateViewModel inState;
    protected StateViewModel outState;
    protected ConditionViewModel preCondition;

    public TransitionViewModel(long id, String name, String type) {
        super(id, name, type);
    }

    public StateViewModel getInState() {
        return inState;
    }

    public void setInState(StateViewModel inState) {
        this.inState = inState;
    }

    public StateViewModel getOutState() {
        return outState;
    }

    public void setOutState(StateViewModel outState) {
        this.outState = outState;
    }

    public ConditionViewModel getPreCondition() {
        return preCondition;
    }

    public void setPreCondition(ConditionViewModel preCondition) {
        this.preCondition = preCondition;
    }
}
