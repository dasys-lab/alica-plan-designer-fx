package de.unikassel.vs.alica.planDesigner.alicamodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class State extends PlanElement {

    public static final String ENTRYPOINT = "entryPoint";
    protected EntryPoint entryPoint;
    protected Plan parentPlan;
    protected final ArrayList<AbstractPlan> plans = new ArrayList<>();
    protected ArrayList<Parametrisation> parametrisations;
    protected ArrayList<Transition> inTransitions;
    protected ArrayList<Transition> outTransitions;

    public State ()
    {
        parametrisations = new ArrayList<>();
        inTransitions = new ArrayList<>();
        outTransitions = new ArrayList<>();
    }

    public State(long id) {
        this.id = id;
    }

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

    public List<AbstractPlan> getPlans() {
        return Collections.unmodifiableList(plans);
    }

    public void addAbstractPlan(AbstractPlan abstractPlan) {
        plans.add(abstractPlan);
        
        List<Variable> variables = null;
        if (abstractPlan instanceof Plan) {
            variables = ((Plan) abstractPlan).getVariables();
        }

        if (abstractPlan instanceof Behaviour) {
            variables = ((Behaviour)abstractPlan).getVariables();
        }

        if (variables != null) {
            variables.forEach(var -> {
                Parametrisation param = new Parametrisation();
                param.setSubPlan(abstractPlan);
                param.setSubVariable(var);
                param.setVariable(null);
                this.getParametrisations().add(param);
            });
        }
        this.parentPlan.setDirty(true);
    }

    public void removeAbstractPlan(AbstractPlan abstractPlan) {
        plans.remove(abstractPlan);

        for (Parametrisation param : parametrisations) {
            if (param.getSubPlan().getId() == abstractPlan.getId()) {
                parametrisations.remove(param);
            }
        }
        this.parentPlan.setDirty(true);
    }

    public void replaceAbstractPlan(AbstractPlan oldAbstractPlan, AbstractPlan newAbstractPlan) {
        plans.remove(oldAbstractPlan);
        addAbstractPlan(newAbstractPlan);
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
