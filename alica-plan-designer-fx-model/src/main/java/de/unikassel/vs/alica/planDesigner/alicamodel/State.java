package de.unikassel.vs.alica.planDesigner.alicamodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class State extends PlanElement {

    public static final String ENTRYPOINT = "entryPoint";
    protected EntryPoint entryPoint;
    protected Plan parentPlan;
    protected final ArrayList<AbstractPlan> plans = new ArrayList<>();
    protected final ArrayList<Parametrisation> parametrisations = new ArrayList<>();
    protected final ArrayList<Transition> inTransitions = new ArrayList<>();
    protected final ArrayList<Transition> outTransitions = new ArrayList<>();

    public State ()
    {
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
            for(Variable var : variables) {
                Parametrisation param = new Parametrisation();
                param.setSubPlan(abstractPlan);
                param.setSubVariable(var);
                param.setVariable(null);
                this.addParametrisation(param);
            }
        }
        this.parentPlan.setDirty(true);
    }

    public void removeAbstractPlan(AbstractPlan abstractPlan) {
        plans.remove(abstractPlan);

        // iterator in order to avoid concurrent modification exception
        Iterator<Parametrisation> iterator = parametrisations.iterator();
        while ((iterator).hasNext()) {
            Parametrisation param = iterator.next();
            if (param.getSubPlan().getId() == abstractPlan.getId()) {
                iterator.remove();
            }
        }
        this.parentPlan.setDirty(true);
    }

    private void addParametrisation(Parametrisation param) {
        this.parametrisations.add(param);
    }
    public List<Parametrisation> getParametrisations() {
        return Collections.unmodifiableList(parametrisations);
    }

    public List<Transition> getInTransitions() {
        return Collections.unmodifiableList(inTransitions);
    }

    public List<Transition> getOutTransitions() {
        return Collections.unmodifiableList(outTransitions);
    }

}
