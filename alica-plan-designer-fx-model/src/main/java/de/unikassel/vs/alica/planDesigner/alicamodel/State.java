package de.unikassel.vs.alica.planDesigner.alicamodel;

import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class State extends PlanElement {

    protected final SimpleObjectProperty<EntryPoint> entryPoint = new SimpleObjectProperty<>();
    protected final SimpleObjectProperty<Plan> parentPlan = new SimpleObjectProperty<>();

    protected final ArrayList<AbstractPlan> abstractPlans = new ArrayList<>();
    protected final ArrayList<Parametrisation> parametrisations = new ArrayList<>();
    protected final ArrayList<Transition> outTransitions = new ArrayList<>();
    protected final ArrayList<Transition> inTransitions = new ArrayList<>();

    private ChangeListenerForDirtyFlag changeListener;

    public State ()
    {
    }

    public State(long id) {
        this.id = id;
    }

    public EntryPoint getEntryPoint() {
        return entryPoint.get();
    }
    public void setEntryPoint(EntryPoint entryPoint) {
        this.entryPoint.set(entryPoint);
    }
    public SimpleObjectProperty<EntryPoint> entryPointProperty() { return entryPoint; }

    public Plan getParentPlan() {
        return parentPlan.get();
    }
    public void setParentPlan(Plan parentPlan) {
        this.parentPlan.set(parentPlan);
    }
    public SimpleObjectProperty<Plan> parentPlanProperty() { return parentPlan; }

    public List<AbstractPlan> getAbstractPlans() {
        return Collections.unmodifiableList(abstractPlans);
    }
    public void addAbstractPlan(AbstractPlan abstractPlan) {
        abstractPlans.add(abstractPlan);
        // TODO Issue #53 on Github
        this.changeListener.setDirty();
    }
    public void removeAbstractPlan(AbstractPlan abstractPlan) {
        abstractPlans.remove(abstractPlan);
        // TODO Issue #53 on Github

        // iterator in order to avoid concurrent modification exception
        Iterator<Parametrisation> iterator = parametrisations.iterator();
        while ((iterator).hasNext()) {
            Parametrisation param = iterator.next();
            if (param.getSubPlan().getId() == abstractPlan.getId()) {
                iterator.remove();
            }
        }
        this.changeListener.setDirty();
    }

    public List<Parametrisation> getParametrisations() {
        return Collections.unmodifiableList(parametrisations);
    }
    public void addParametrisation(Parametrisation param) {
        this.parametrisations.add(param);
        param.registerDirtyFlag(this.changeListener);
        this.changeListener.setDirty();
    }
    public void removeParametrisation(Parametrisation param) {
        this.parametrisations.remove(param);
        this.changeListener.setDirty();
    }

    public List<Transition> getOutTransitions() {
        return Collections.unmodifiableList(outTransitions);
    }
    public void addOutTransition(Transition transition) {
        this.outTransitions.add(transition);
    }
    public void removeOutTransition(Transition transition) {
        this.outTransitions.remove(transition);
    }

    public List<Transition> getInTransitions() {
        return Collections.unmodifiableList(inTransitions);
    }
    public void addInTransition(Transition transition) {
        this.inTransitions.add(transition);
    }
    public void removeInTransition(Transition transition) {
        this.inTransitions.remove(transition);
    }

    public void registerDirtyFlag(ChangeListenerForDirtyFlag listener) {
        this.changeListener = listener;
        this.name.addListener(listener);
        this.comment.addListener(listener);

        for (Parametrisation param : parametrisations) {
            param.registerDirtyFlag(listener);
        }
    }
}
