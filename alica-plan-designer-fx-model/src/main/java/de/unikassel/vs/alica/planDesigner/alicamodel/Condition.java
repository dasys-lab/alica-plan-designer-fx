package de.unikassel.vs.alica.planDesigner.alicamodel;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.List;

public class Condition extends PlanElement {

    protected final SimpleStringProperty conditionString = new SimpleStringProperty();
    protected final SimpleStringProperty pluginName = new SimpleStringProperty();
    protected final SimpleBooleanProperty enabled = new SimpleBooleanProperty();

    // Usage of ObservableList to simplify the dirty-listener. Through the private setter-methods
    // the ObservableList is 'hidden' from the deserializer (deserialized like a normal List)
    protected ObservableList<Variable> variables = FXCollections.observableArrayList();
    protected ObservableList<Quantifier> quantifiers = FXCollections.observableArrayList();
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

    public List<Variable> getVariables() {
        return variables;
    }

    // Setter needed for deserialization
    private void setVariables(List<Variable> variables) {
        this.variables = FXCollections.observableArrayList(variables);
    }

    public void addVariable(Variable variable){
       variables.add(variable);
    }

    public void removeVariable(Variable variable){
        variables.remove(variable);
    }

    public List<Quantifier> getQuantifiers() {
        return quantifiers;
    }

    // Setter needed for deserialization
    private void setQuantifiers(List<Quantifier> quantifiers) {
        this.quantifiers = FXCollections.observableArrayList(quantifiers);
    }

    public void addQuantifier(Quantifier quantifier){
        quantifiers.add(quantifier);
    }

    public void removeQuantifier(Quantifier quantifier){
        quantifiers.remove(quantifier);
    }

    public void registerDirtyFlagToAbstractPlan(AbstractPlan abstractPlan) {
        InvalidationListener dirty = obs -> abstractPlan.setDirty(true);

        this.nameProperty().addListener(dirty);
        this.commentProperty().addListener(dirty);
        this.pluginNameProperty().addListener(dirty);
        this.conditionStringProperty().addListener(dirty);
        this.enabledProperty().addListener(dirty);
        this.variables.addListener(dirty);
        this.quantifiers.addListener((ListChangeListener<? super Quantifier>) change -> {
            abstractPlan.setDirty(true);
            while(change.next()) {
                for(Quantifier quantifier : change.getAddedSubList()) {
                    quantifier.registerListenerToAbstractPlan(abstractPlan);
                }
            }
        });

        for(Quantifier quantifier : quantifiers) {
            quantifier.registerListenerToAbstractPlan(abstractPlan);
        }
        // Variables itself are already part of an AbstractPlan, so listeners exist already
    }
}