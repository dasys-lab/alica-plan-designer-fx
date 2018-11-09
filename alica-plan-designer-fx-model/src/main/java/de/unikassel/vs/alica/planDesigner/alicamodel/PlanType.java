package de.unikassel.vs.alica.planDesigner.alicamodel;

import java.util.ArrayList;

public class PlanType extends AbstractPlan {

    private ArrayList<Parametrisation> parametrisations;

    private ArrayList<AnnotatedPlan> plans;

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
