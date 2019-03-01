package de.unikassel.vs.alica.planDesigner.alicamodel;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Condition extends PlanElement {

    protected final SimpleStringProperty conditionString = new SimpleStringProperty();
    protected final SimpleStringProperty pluginName = new SimpleStringProperty();
    protected final SimpleBooleanProperty enabled = new SimpleBooleanProperty();

    protected ArrayList<Variable> variables = new ArrayList<>();
    protected ArrayList<Quantifier> quantifiers = new ArrayList<>();

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
        return variables;
    }

    public void addVariable(Variable variable){
       variables.add(variable);
    }

    public void removeVariable(Variable variable){
        variables.remove(variable);
    }

    public ArrayList<Quantifier> getQuantifiers() {
        return quantifiers;
    }

    public void addQuantifier(Quantifier quantifier){
        quantifiers.add(quantifier);
    }

    public void removeQuantifier(Quantifier quantifier){
        quantifiers.remove(quantifier);
    }
}
