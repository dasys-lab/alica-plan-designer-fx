package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import java.util.ArrayList;

public class PlanType extends AbstractPlan {
    ArrayList<Parametrisation> parametrisations;

    ArrayList<AnnotatedPlan> plans;

    public PlanType () {
        parametrisations = new ArrayList<>();
        plans = new ArrayList<>();
    }

    public ArrayList<Parametrisation> getParametrisations() {
        return parametrisations;
    }

    public ArrayList<AnnotatedPlan> getPlans() {
        return plans;
    }
}
