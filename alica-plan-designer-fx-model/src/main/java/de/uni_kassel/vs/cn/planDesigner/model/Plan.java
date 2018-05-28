package de.uni_kassel.vs.cn.planDesigner.model;

import java.util.ArrayList;

public class Plan extends AbstractPlan{

    protected boolean masterPlan;
    protected double utilityThreshold;
    protected PreCondition preCondition;
    protected RuntimeCondition runtimeCondition;
    protected ArrayList<Transition> transitions;
    protected ArrayList<State> states;
    protected ArrayList<Synchronization> synchronizations;
    protected ArrayList<EntryPoint> entryPoints;
    protected ArrayList<Variable> variables;

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
        return utilityThreshold;
    }

    public void setUtilityThreshold(double utilityThreshold) {
        this.utilityThreshold = utilityThreshold;
    }

    public boolean getMasterPlan() {
        return masterPlan;
    }

    public void setMasterPlan(boolean masterPlan) {
        this.masterPlan = masterPlan;
    }
}
