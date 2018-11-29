package de.unikassel.vs.alica.planDesigner.view.model;

import java.util.LinkedList;
import java.util.List;

public class TransitionViewModel extends PlanElementViewModel {

    public static final String INSTATE = "inState";
    public static final String OUTSTATE = "outState";

    protected StateViewModel inState;
    protected StateViewModel outState;
    protected ConditionViewModel preCondition;
    private List<BendPointViewModel> bendpoints;

    public TransitionViewModel(long id, String name, String type) {
        super(id, name, type);
        this.bendpoints = new LinkedList<>();
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

    public List<BendPointViewModel> getBendpoints() {
        return bendpoints;
    }

    public void setBendpoints(List<BendPointViewModel> bendpoints) {
        this.bendpoints = bendpoints;
    }
}
