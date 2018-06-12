package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.deserialization.CustomTaskDeserializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.CustomFileReferenceSerializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.CustomPlanElementSerializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.CustomTaskSerializer;

public class EntryPoint extends PlanElement {
    @JsonSerialize(using = CustomTaskSerializer.class)
    @JsonDeserialize(using = CustomTaskDeserializer.class)
    protected Task task;
    protected boolean successRequired;
    @JsonSerialize(using = CustomPlanElementSerializer.class)
    protected State state;
    @JsonSerialize(using = CustomPlanElementSerializer.class)
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
