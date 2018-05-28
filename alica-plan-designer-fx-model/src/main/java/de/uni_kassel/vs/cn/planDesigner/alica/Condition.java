package de.uni_kassel.vs.cn.planDesigner.alica;

import java.util.ArrayList;

public class Condition extends PlanElement {
    protected String conditionString;
    protected String pluginName;
    protected ArrayList<Variable> vars;
    protected ArrayList<Quantifier> quantifier;

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

    public ArrayList<Variable> getVars() {
        return vars;
    }

    public ArrayList<Quantifier> getQuantifier() {
        return quantifier;
    }
}
