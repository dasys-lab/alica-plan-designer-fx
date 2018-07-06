package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import java.util.ArrayList;

public class Behaviour extends AbstractPlan {

    protected ArrayList<PreCondition> preConditions;
    protected ArrayList<RuntimeCondition> runtimeConditions;
    protected ArrayList<PostCondition> postConditions;
    protected ArrayList<Variable> variables;
    protected int frequency;
    protected long deferring;

    public Behaviour() {
        variables = new ArrayList<>();
        preConditions = new ArrayList<>();
        runtimeConditions = new ArrayList<>();
        postConditions = new ArrayList<>();
    }

    public ArrayList<PreCondition> getPreConditions() {
        return preConditions;
    }

    public ArrayList<RuntimeCondition> getRuntimeConditions() {
        return runtimeConditions;
    }

    public ArrayList<PostCondition> getPostConditions() {
        return postConditions;
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

    public long getDeferring() {
        return deferring;
    }

    public void setDeferring(long deferring) {
        this.deferring = deferring;
    }
}
