package de.uni_kassel.vs.cn.planDesigner.alicamodel;

public class SerializablePlanElement extends PlanElement {
    protected String relativeDirectory;

    public String getRelativeDirectory() {
        return relativeDirectory;
    }

    public void setRelativeDirectory(String relativeDirectory) {
        this.relativeDirectory = relativeDirectory;
    }
}
