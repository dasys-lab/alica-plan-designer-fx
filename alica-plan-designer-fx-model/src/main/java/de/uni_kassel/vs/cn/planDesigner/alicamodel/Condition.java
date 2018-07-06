package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import java.util.ArrayList;

public class Condition extends PlanElement {
    protected String conditionString;
    protected String pluginName;
    protected ArrayList<Variable> vars;
    protected ArrayList<Quantifier> quantifier;
    protected boolean enabled;

    public boolean getEnabled () {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getConditionString() {
        return conditionString;
    }

    public void setConditionString(String conditionString) {
        this.conditionString = conditionString;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public ArrayList<Variable> getVariables() {
        return vars;
    }

    public ArrayList<Quantifier> getQuantifiers() {
        return quantifier;
    }
}
