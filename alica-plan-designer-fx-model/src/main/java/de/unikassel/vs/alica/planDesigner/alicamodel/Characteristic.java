package de.unikassel.vs.alica.planDesigner.alicamodel;


import javafx.beans.property.SimpleDoubleProperty;

public class Characteristic extends PlanElement {

    protected final SimpleDoubleProperty weight = new SimpleDoubleProperty();

    protected Capability capability;
    protected CapValue value;

    public double getWeight() {
        return this.weight.get();
    }
    public void setWeight(double weight) {
        this.weight.set(weight);
    }
    public SimpleDoubleProperty weightProperty() {
        return weight;
    }

    public Capability getCapability() {
        return this.capability;
    }
    public void setCapability(Capability capability) {
        this.capability = capability;
    }

    public CapValue getValue() {
        return this.value;
    }
    public void setValue(CapValue value) {
        this.value = value;
    }

    public void addListener(ChangeListenerForDirtyFlag listener) {
        weight.addListener(listener);
    }
}
