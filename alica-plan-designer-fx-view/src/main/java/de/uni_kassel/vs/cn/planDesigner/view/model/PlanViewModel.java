package de.uni_kassel.vs.cn.planDesigner.view.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class PlanViewModel extends SerializableViewModel {

    protected final BooleanProperty masterPlan = new SimpleBooleanProperty();
    protected final DoubleProperty utilityThreshold = new SimpleDoubleProperty();
    protected ObservableList<EntryPointViewModel> entryPoints;
    protected ObservableList<StateViewModel> states;
    protected ObservableList<TransitionViewModel> transitions;
    protected ObservableList<ConditionViewModel> conditions;
    protected ObservableList<SynchronizationViewModel> synchronisations;

    public PlanViewModel(long id, String name, String type) {
        super(id, name, type);
        this.entryPoints = FXCollections.observableArrayList(new ArrayList<>());
        this.states =  FXCollections.observableArrayList(new ArrayList<>());
        this.transitions =  FXCollections.observableArrayList(new ArrayList<>());
        this.conditions = FXCollections.observableArrayList(new ArrayList<>());
        this.synchronisations = FXCollections.observableArrayList(new ArrayList<>());
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

    public boolean isMasterPlan() {
        return masterPlan.get();
    }

    public ObservableList<EntryPointViewModel> getEntryPoints() {
        return entryPoints;
    }

    public void setEntryPoints(ObservableList<EntryPointViewModel> entryPoints) {
        this.entryPoints = entryPoints;
    }

    public ObservableList<StateViewModel> getStates() {
        return states;
    }

    public void setStates(ObservableList<StateViewModel> states) {
        this.states = states;
    }

    public ObservableList<TransitionViewModel> getTransitions() {
        return transitions;
    }

    public void setTransitions(ObservableList<TransitionViewModel> transitions) {
        this.transitions = transitions;
    }

    public ObservableList<ConditionViewModel> getConditions() {
        return conditions;
    }

    public void setConditions(ObservableList<ConditionViewModel> conditions) {
        this.conditions = conditions;
    }

    public ObservableList<SynchronizationViewModel> getSynchronisations() {
        return synchronisations;
    }

    public void setSynchronisations(ObservableList<SynchronizationViewModel> synchronisations) {
        this.synchronisations = synchronisations;
    }
}
