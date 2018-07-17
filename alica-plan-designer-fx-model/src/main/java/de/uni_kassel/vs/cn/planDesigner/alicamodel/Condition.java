package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

public class Condition extends PlanElement {

    protected final SimpleStringProperty conditionString = new SimpleStringProperty();
    protected final SimpleStringProperty pluginName = new SimpleStringProperty();
    protected final SimpleBooleanProperty enabled = new SimpleBooleanProperty();

    protected ArrayList<Variable> vars;
    protected ArrayList<Quantifier> quantifier;

    public boolean getEnabled () {
        return enabled.get();
    }

    public void setEnabled(boolean enabled) {
        this.enabled.set(enabled);
    }

    public SimpleBooleanProperty enabledProperty() {
        return enabled;
    }

    public String getConditionString() {
        return conditionString.get();
    }

    public void setConditionString(String conditionString) {
        this.conditionString.set(conditionString);
    }

    public SimpleStringProperty conditionStringProperty() {
        return conditionString;
    }

    public String getPluginName() {
        return pluginName.get();
    }

    public void setPluginName(String pluginName) {
        this.pluginName.set(pluginName);
    }

    public SimpleStringProperty pluginNameProperty() {
        return pluginName;
    }

    public ArrayList<Variable> getVariables() {
        return vars;
    }

    public ArrayList<Quantifier> getQuantifiers() {
        return quantifier;
    }
}
