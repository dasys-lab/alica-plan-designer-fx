package de.uni_kassel.vs.cn.planDesigner.alicamodel;

public class SerializablePlanElement extends PlanElement {
    protected String destinationPath;

    public String getDestinationPath() {
        return destinationPath;
    }

    public void setDestinationPath(String destinationPath) {
        this.destinationPath = destinationPath;
    }
}
