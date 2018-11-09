package de.unikassel.vs.alica.planDesigner.alicamodel;

import javafx.beans.property.*;

import java.util.ArrayList;

public class Plan extends AbstractPlan {

    protected final SimpleBooleanProperty masterPlan = new SimpleBooleanProperty();
    protected final SimpleDoubleProperty utilityThreshold = new SimpleDoubleProperty();

    protected PreCondition preCondition;
    protected RuntimeCondition runtimeCondition;
    protected ArrayList<EntryPoint> entryPoints;
    protected ArrayList<State> states;
    protected ArrayList<Transition> transitions;
    protected ArrayList<Synchronization> synchronizations;
    protected ArrayList<Variable> variables;

    public Plan() {
        super();
        transitions = new ArrayList<>();
        states = new ArrayList<>();
        synchronizations = new ArrayList<>();
        entryPoints = new ArrayList<>();
        variables = new ArrayList<>();
    }

    public Plan(long id) {
        this.id = id ;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public ArrayList<State> getStates() {
        return states;
    }

    public ArrayList<Synchronization> getSynchronizations() {
        return synchronizations;
    }

    public ArrayList<EntryPoint> getEntryPoints() {
        return entryPoints;
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public PreCondition getPreCondition() {
        return preCondition;
    }

    public void setPreCondition(PreCondition preCondition) {
        this.preCondition = preCondition;
    }

    public RuntimeCondition getRuntimeCondition() {
        return runtimeCondition;
    }

    public void setRuntimeCondition(RuntimeCondition runtimeCondition) {
        this.runtimeCondition = runtimeCondition;
    }

    public double getUtilityThreshold() {
        return utilityThreshold.get();
    }

    public void setUtilityThreshold(double utilityThreshold) {
        this.utilityThreshold.set(utilityThreshold);
    }

    public SimpleDoubleProperty utilityThreshold() {
        return utilityThreshold;
    }

    public boolean getMasterPlan() {
        return masterPlan.get();
    }

    public void setMasterPlan(boolean masterPlan) {
        this.masterPlan.set(masterPlan);
    }

    public SimpleBooleanProperty masterPlanProperty() {
        return masterPlan;
    }
}
