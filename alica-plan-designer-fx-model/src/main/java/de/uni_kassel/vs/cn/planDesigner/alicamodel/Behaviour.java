package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;

import java.util.ArrayList;

public class Behaviour extends AbstractPlan {
    protected final SimpleIntegerProperty frequency = new SimpleIntegerProperty();
    protected final SimpleLongProperty deferring = new SimpleLongProperty();

    protected PreCondition preCondition;
    protected RuntimeCondition runtimeCondition;
    protected PostCondition postCondition;
    protected ArrayList<Variable> variables;

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
        return frequency.get();
    }

    public void setFrequency(int frequency) {
        this.frequency.set(frequency);
    }

    public SimpleIntegerProperty frequencyProperty() {
        return frequency;
    }

    public long getDeferring() {
        return deferring.get();
    }

    public void setDeferring(long deferring) {
        this.deferring.set(deferring);
    }

    public SimpleLongProperty deferringProperty() {
        return this.deferring;
    }


}
