package de.uni_kassel.vs.cn.planDesigner.view.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

public class ConditionViewModel extends PlanElementViewModel {

    protected StringProperty conditionString;
    protected StringProperty pluginName;
    protected BooleanProperty enabled;
    protected ArrayList<VariableViewModel> vars;
    protected ArrayList<QuantifierViewModel> quantifier;

    public ConditionViewModel(long id, String name, String type) {
        super(id, name, type);
        this.vars = new ArrayList<>();
        this.quantifier = new ArrayList<>();
    }

    public String getConditionString() {
        return conditionString.get();
    }

    public StringProperty conditionStringProperty() {
        return conditionString;
    }

    public void setConditionString(String conditionString) {
        this.conditionString.set(conditionString);
    }

    public String getPluginName() {
        return pluginName.get();
    }

    public StringProperty pluginNameProperty() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName.set(pluginName);
    }

    public boolean isEnabled() {
        return enabled.get();
    }

    public BooleanProperty enabledProperty() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled.set(enabled);
    }

    public ArrayList<VariableViewModel> getVars() {
        return vars;
    }

    public ArrayList<QuantifierViewModel> getQuantifier() {
        return quantifier;
    }
}
