package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import java.util.ArrayList;

public class Plan extends AbstractPlan implements IInhabitable {

    protected boolean masterPlan;
    protected boolean activated;
    protected double utilityThreshold;
    protected PreCondition preCondition;
    protected RuntimeCondition runtimeCondition;
    protected ArrayList<EntryPoint> entryPoints;
    protected ArrayList<State> states;
    protected ArrayList<Transition> transitions;
    protected ArrayList<Synchronization> synchronizations;
    protected ArrayList<Variable> variables;

    public Plan() {
        transitions = new ArrayList<>();
        states = new ArrayList<>();
        synchronizations = new ArrayList<>();
        entryPoints = new ArrayList<>();
        variables = new ArrayList<>();
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

    public boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
