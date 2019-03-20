package de.unikassel.vs.alica.planDesigner.alicamodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlanType extends AbstractPlan {

    protected final ArrayList<Parametrisation> parametrisations = new ArrayList<>();
    protected final ArrayList<AnnotatedPlan> annotatedPlans = new ArrayList<>();

    public void addParametrisation(Parametrisation parametrisation) {
        parametrisations.add(parametrisation);
        parametrisation.registerDirtyFlag(this.changeListenerForDirtyFlag);
        this.changeListenerForDirtyFlag.setDirty();
    }
    public void removeParametrisation(Parametrisation parametrisation) {
        parametrisations.remove(parametrisation);
        this.changeListenerForDirtyFlag.setDirty();
    }
    public List<Parametrisation> getParametrisations() {
        return Collections.unmodifiableList(parametrisations);
    }

    public void addAnnotatedPlan(AnnotatedPlan annotatedPlan) {
        annotatedPlans.add(annotatedPlan);
        annotatedPlan.registerDirtyFlag(this.changeListenerForDirtyFlag);
        this.changeListenerForDirtyFlag.setDirty();
    }
    public void removeAnnotatedPlan(AnnotatedPlan annotatedPlan) {
        annotatedPlans.remove(annotatedPlan);
        this.changeListenerForDirtyFlag.setDirty();
    }
    public List<AnnotatedPlan> getAnnotatedPlans() {
        return Collections.unmodifiableList(annotatedPlans);
    }

    public void registerDirtyFlag() {
        super.registerDirtyFlag();
    }
}
