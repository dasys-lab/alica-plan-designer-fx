package de.unikassel.vs.alica.planDesigner.alicamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Condition extends PlanElement {

    protected final SimpleStringProperty conditionString = new SimpleStringProperty();
    protected final SimpleStringProperty pluginName = new SimpleStringProperty();
    protected final SimpleBooleanProperty enabled = new SimpleBooleanProperty();

    protected ArrayList<Variable> variables = new ArrayList<>();
    protected ArrayList<Quantifier> quantifiers = new ArrayList<>();

    @JsonIgnore
    private ArrayList<InvalidationListener> listeners = new ArrayList<>();

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

       for(InvalidationListener listener : listeners){
           variable.nameProperty().addListener(listener);
           variable.variableTypeProperty().addListener(listener);
           variable.commentProperty().addListener(listener);

           listener.invalidated(null);
       }
    }

    public void removeVariable(Variable variable){
        variables.remove(variable);

        for(InvalidationListener listener : listeners){
            variable.nameProperty().removeListener(listener);
            variable.variableTypeProperty().removeListener(listener);
            variable.commentProperty().removeListener(listener);

            listener.invalidated(null);
        }
    }

    public ArrayList<Quantifier> getQuantifiers() {
        return quantifiers;
    }

    public void addQuantifier(Quantifier quantifier){
        quantifiers.add(quantifier);

        for(InvalidationListener listener : listeners){
            quantifier.nameProperty().addListener(listener);
            quantifier.quantifierTypeProperty().addListener(listener);
            quantifier.commentProperty().addListener(listener);

            listener.invalidated(null);
        }
    }

    public void removeQuantifier(Quantifier quantifier){
        quantifiers.remove(quantifier);

        for(InvalidationListener listener : listeners){
            quantifier.nameProperty().removeListener(listener);
            quantifier.quantifierTypeProperty().removeListener(listener);
            quantifier.commentProperty().removeListener(listener);

            listener.invalidated(null);
        }
    }

    public void addListenerToAllProperties(InvalidationListener listener){
        listeners.add(listener);

        nameProperty().addListener(listener);
        commentProperty().addListener(listener);
        conditionStringProperty().addListener(listener);
        enabledProperty().addListener(listener);
        pluginNameProperty().addListener(listener);

        for(Variable variable : variables){
            variable.nameProperty().addListener(listener);
            variable.variableTypeProperty().addListener(listener);
            variable.commentProperty().addListener(listener);
        }

        for(Quantifier quantifier :quantifiers){
            quantifier.nameProperty().addListener(listener);
            quantifier.quantifierTypeProperty().addListener(listener);
            quantifier.commentProperty().addListener(listener);
        }
    }
}
