package de.uni_kassel.vs.cn.planDesigner.alicamodel;

public class PreCondition extends Condition {

    protected boolean enabled;

    boolean getEnabled () {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
