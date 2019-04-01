package de.unikassel.vs.alica.planDesigner.view.model;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.Types;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;

public class PlanViewModel extends SerializableViewModel implements HasVariablesView{

    protected final BooleanProperty masterPlan = new SimpleBooleanProperty(null, "masterPlan", false);
    protected final DoubleProperty utilityThreshold = new SimpleDoubleProperty(null, "utilityThreshold", 0.5);
    protected final ObjectProperty<ConditionViewModel> preCondition = new SimpleObjectProperty<>(null, Types.PRECONDITION, null);
    protected final ObjectProperty<ConditionViewModel> runtimeCondition = new SimpleObjectProperty<>(null, Types.RUNTIMECONDITION, null);
    protected final ObservableList<EntryPointViewModel> entryPoints = FXCollections.observableArrayList(new ArrayList<>());
    protected final ObservableList<StateViewModel> states = FXCollections.observableArrayList(new ArrayList<>());
    protected final ObservableList<TransitionViewModel> transitions = FXCollections.observableArrayList(new ArrayList<>());
    protected final ObservableList<SynchronisationViewModel> synchronisations = FXCollections.observableArrayList(new ArrayList<>());
    protected final ObservableList<VariableViewModel> variables = FXCollections.observableArrayList(new ArrayList<>());

    public PlanViewModel(long id, String name, String type) {
        super(id, name, type);

        this.uiPropertyList.clear();
        this.uiPropertyList.addAll(Arrays.asList("name", "id", "comment", "masterPlan", "relativeDirectory", "utilityThreshold"));
        this.masterPlanProperty().addListener((observable, oldValue, newValue)
                -> this.setType(newValue ? Types.MASTERPLAN : Types.PLAN));
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

    public final ObjectProperty<ConditionViewModel> preConditionProperty(){
        return preCondition;
    }
    public void setPreCondition(ConditionViewModel condition){
        this.preCondition.set(condition);
    }
    public ConditionViewModel getPreCondition(){
        return this.preCondition.get();
    }

    public final ObjectProperty<ConditionViewModel> runtimeConditionProperty(){
        return runtimeCondition;
    }
    public void setRuntimeCondition(ConditionViewModel condition){
        this.runtimeCondition.set(condition);
    }
    public ConditionViewModel getRuntimeCondition(){
        return this.runtimeCondition.get();
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

    public ObservableList<SynchronisationViewModel> getSynchronisations() {
        return synchronisations;
    }

    public ObservableList<VariableViewModel> getVariables() {
        return variables;
    }
}
