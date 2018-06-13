package de.uni_kassel.vs.cn.planDesigner.alicamodel;

public class EntryPoint extends PlanElement {
    protected Task task;
    protected boolean successRequired;
    protected State state;
    protected Plan plan;
    protected int minCardinality;
    protected int maxCardinality;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public boolean getSuccessRequired() {
        return successRequired;
    }

    public void setSuccessRequired(boolean successRequired) {
        this.successRequired = successRequired;
    }

    public int getMinCardinality() {
        return minCardinality;
    }

    public void setMinCardinality(int minCardinality) {
        this.minCardinality = minCardinality;
    }

    public int getMaxCardinality() {
        return maxCardinality;
    }

    public void setMaxCardinality(int maxCardinality) {
        this.maxCardinality = maxCardinality;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
}
