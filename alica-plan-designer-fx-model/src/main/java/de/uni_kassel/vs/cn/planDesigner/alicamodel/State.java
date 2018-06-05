package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import java.util.ArrayList;

public class State extends PlanElement implements IInhabitable {
    protected EntryPoint entryPoint;
    protected Plan parentPlan;
    protected ArrayList<Plan> plans;
    protected ArrayList<Parametrisation> parametrisations;
    protected ArrayList<Transition> inTransitions;
    protected ArrayList<Transition> outTransitions;

    public EntryPoint getEntryPoint() {
        return entryPoint;
    }

    public Plan getParentPlan() {
        return parentPlan;
    }

    public void setParentPlan(Plan parentPlan) {
        this.parentPlan = parentPlan;
    }

    public void setEntryPoint(EntryPoint entryPoint) {
        this.entryPoint = entryPoint;
    }

    public ArrayList<Plan> getPlans() {
        return plans;
    }

    public ArrayList<Parametrisation> getParametrisations() {
        return parametrisations;
    }

    public ArrayList<Transition> getInTransitions() {
        return inTransitions;
    }

    public ArrayList<Transition> getOutTransitions() {
        return outTransitions;
    }

}
