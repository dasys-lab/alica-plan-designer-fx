package de.unikassel.vs.alica.planDesigner.view.model;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ConditionViewModel extends PlanElementViewModel {

    protected StringProperty conditionString = new SimpleStringProperty(this, "conditionString", null);
    protected StringProperty pluginName = new SimpleStringProperty(this, "pluginName", null);
    protected BooleanProperty enabled = new SimpleBooleanProperty(this, "enabled", false);
    protected final ObservableList<VariableViewModel> variables;
    protected final ObservableList<QuantifierViewModel> quantifier;

    public ConditionViewModel(long id, String name, String type) {
        super(id, name, type);
        this.variables = FXCollections.observableArrayList(new ArrayList<>());
        this.quantifier = FXCollections.observableArrayList(new ArrayList<>());
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

    public ObservableList<VariableViewModel> getVariables() {
        return variables;
    }

    public ObservableList<QuantifierViewModel> getQuantifiers() {
        return quantifier;
    }

    @Override
    public void registerListener(IGuiModificationHandler handler) {
        super.registerListener(handler);
        this.enabled.addListener((observable, oldValue, newValue) ->
                fireGUIAttributeChangeEvent(handler, newValue, enabled.getClass().getSimpleName(), enabled.getName()));
        this.conditionString.addListener((observable, oldValue, newValue) ->
                fireGUIAttributeChangeEvent(handler, newValue, conditionString.getClass().getSimpleName()
                        , conditionString.getName()));
    }
}
