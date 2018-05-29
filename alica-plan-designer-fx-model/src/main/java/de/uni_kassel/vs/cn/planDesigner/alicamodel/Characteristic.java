package de.uni_kassel.vs.cn.planDesigner.alicamodel;


public class Characteristic extends PlanElement {

    protected double weight;
    protected Capability capabilty;
    protected CapValue value;


    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Capability getCapabilty() {
        return this.capabilty;
    }

    public void setCapabilty(Capability capabilty) {
        this.capabilty = capabilty;
    }

    public CapValue getValue() {
        return this.value;
    }

    public void setValue(CapValue value) {
        this.value = value;
    }


}
