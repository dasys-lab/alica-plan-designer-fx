package de.unikassel.vs.alica.planDesigner.alicamodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlanType extends AbstractPlan {

    protected final ArrayList<Parametrisation> parametrisations = new ArrayList<>();
    protected final ArrayList<AnnotatedPlan> plans = new ArrayList<>();

    public void registerDirtyFlag() {
        super.registerDirtyFlag();
    }

    public void addParametrisation(Parametrisation parametrisation) {
        parametrisations.add(parametrisation);
        this.setDirty(true);
    }

    public void removeParametrisation(Parametrisation parametrisation) {
        parametrisations.remove(parametrisation);
        this.setDirty(true);
    }

    public List<Parametrisation> getParametrisations() {
        return Collections.unmodifiableList(parametrisations);
    }

    public void addPlan(AnnotatedPlan annotatedPlan) {
        plans.add(annotatedPlan);
        this.setDirty(true);
    }

    public void removePlan(AnnotatedPlan annotatedPlan) {
        plans.remove(annotatedPlan);
        this.setDirty(true);
    }

    public List<AnnotatedPlan> getPlans() {
        return Collections.unmodifiableList(plans);
    }
}
