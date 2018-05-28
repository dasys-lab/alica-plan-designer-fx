package de.uni_kassel.vs.cn.planDesigner.alica;

import java.util.ArrayList;

public class PlanType extends AbstractPlan {
    ArrayList<Parametrisation> parametrisations;
    ArrayList<Plan> plans;

    public ArrayList<Parametrisation> getParametrisations() {
        return parametrisations;
    }

    public ArrayList<Plan> getPlans() {
        return plans;
    }
}
