package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.serialization.CustomArrayFileReferenceSerializer;

import java.util.ArrayList;

public class PlanType extends AbstractPlan {
    ArrayList<Parametrisation> parametrisations;

    @JsonSerialize(using = CustomArrayFileReferenceSerializer.class)
    ArrayList<Plan> plans;

    public PlanType () {
        parametrisations = new ArrayList<>();
        plans = new ArrayList<>();
    }

    public ArrayList<Parametrisation> getParametrisations() {
        return parametrisations;
    }

    public ArrayList<Plan> getPlans() {
        return plans;
    }
}
