package de.unikassel.vs.alica.planDesigner.view.model;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;

public class PlanViewModel extends SerializableViewModel {

    protected final BooleanProperty masterPlan = new SimpleBooleanProperty(null, "masterPlan", false);
    protected final DoubleProperty utilityThreshold = new SimpleDoubleProperty(null, "utilityThreshold", 0.5);
    protected final ObservableList<EntryPointViewModel> entryPoints = FXCollections.observableArrayList(new ArrayList<>());
    protected final ObservableList<StateViewModel> states = FXCollections.observableArrayList(new ArrayList<>());
    protected final ObservableList<TransitionViewModel> transitions = FXCollections.observableArrayList(new ArrayList<>());
    protected final ObservableList<ConditionViewModel> conditions = FXCollections.observableArrayList(new ArrayList<>());
    protected final ObservableList<SynchronizationViewModel> synchronisations = FXCollections.observableArrayList(new ArrayList<>());
    protected final ObservableList<VariableViewModel> variables = FXCollections.observableArrayList(new ArrayList<>());

    public PlanViewModel(long id, String name, String type) {
        super(id, name, type);

        this.uiPropertyList.clear();
        this.uiPropertyList.addAll(Arrays.asList("name", "id", "comment", "masterPlan", "relativeDirectory", "utilityThreshold"));
    }

    public void registerListener(IGuiModificationHandler handler) {
        super.registerListener(handler);
        masterPlan.addListener((observable, oldValue, newValue) -> {
            fireGUIAttributeChangeEvent(handler, newValue, masterPlan.getClass().getSimpleName(), masterPlan.getName());
        });
        utilityThreshold.addListener((observable, oldValue, newValue) -> {
            fireGUIAttributeChangeEvent(handler, newValue, utilityThreshold.getClass().getSimpleName(), utilityThreshold.getName());
        });
    }

    public final BooleanProperty masterPlanProperty() {return masterPlan; }
    public void setMasterPlan(boolean masterPlan) {
        this.masterPlan.setValue(masterPlan);
    }
    public boolean getMasterPlan() {
        return masterPlan.get();
    }

    public final DoubleProperty utilityThresholdProperty() {return utilityThreshold;}
    public void setUtilityThreshold(double utilityThreshold) {
        this.utilityThreshold.setValue(utilityThreshold);
    }
    public double getUtilityThreshold() {
        return utilityThreshold.get();
    }

    public ObservableList<EntryPointViewModel> getEntryPoints() {
        return entryPoints;
    }

    public ObservableList<StateViewModel> getStates() {
        return states;
    }

    public ObservableList<TransitionViewModel> getTransitions() {
        return transitions;
    }

    public ObservableList<ConditionViewModel> getConditions() {
        return conditions;
    }

    public ObservableList<SynchronizationViewModel> getSynchronisations() {
        return synchronisations;
    }

    public ObservableList<VariableViewModel> getVariables() {
        return variables;
    }
}
