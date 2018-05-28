package de.uni_kassel.vs.cn.planDesigner.alica;

import java.util.ArrayList;

public class Behaviour extends AbstractPlan {

    protected PreCondition preCondition;
    protected RuntimeCondition runtimeCondition;
    protected PostCondition postCondition;
    protected ArrayList<Variable> variables;
    protected int frequency;

    public Behaviour() {
        variables = new ArrayList<>();
    }

    public PreCondition getPreCondition() {
        return preCondition;
    }

    public void setPreCondition(PreCondition preCondition) {
        this.preCondition = preCondition;
    }

    public RuntimeCondition getRuntimeCondition() {
        return runtimeCondition;
    }

    public void setRuntimeCondition(RuntimeCondition runtimeCondition) {
        this.runtimeCondition = runtimeCondition;
    }

    public PostCondition getPostCondition() {
        return postCondition;
    }

    public void setPostCondition(PostCondition postCondition) {
        this.postCondition = postCondition;
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
