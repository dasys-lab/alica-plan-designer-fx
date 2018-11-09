package de.unikassel.vs.alica.planDesigner.alicamodel;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class EntryPoint extends PlanElement {

    protected final SimpleBooleanProperty successRequired = new SimpleBooleanProperty();
    protected final SimpleIntegerProperty minCardinality = new SimpleIntegerProperty();
    protected final SimpleIntegerProperty maxCardinality = new SimpleIntegerProperty();

    protected Task task;
    protected State state;
    protected Plan plan;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public boolean getSuccessRequired() {
        return successRequired.get();
    }

    public void setSuccessRequired(boolean successRequired) {
        this.successRequired.set(successRequired);
    }

    public SimpleBooleanProperty successRequiredProperty() {
        return successRequired;
    }

    public int getMinCardinality() {
        return minCardinality.get();
    }

    public void setMinCardinality(int minCardinality) {
        this.minCardinality.set(minCardinality);
    }

    public SimpleIntegerProperty minCardinalityProperty() {
        return minCardinality;
    }

    public int getMaxCardinality() {
        return maxCardinality.get();
    }

    public void setMaxCardinality(int maxCardinality) {
        this.maxCardinality.set(maxCardinality);
    }

    public SimpleIntegerProperty maxCardinalityProperty() {
        return maxCardinality;
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
