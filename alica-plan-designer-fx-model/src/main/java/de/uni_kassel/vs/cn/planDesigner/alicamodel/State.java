package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.deserialization.CustomArrayDeserializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.CustomArrayFileReferenceSerializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.CustomArraySerializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.CustomPlanElementSerializer;

import java.util.ArrayList;
import java.util.List;

public class State extends PlanElement implements IInhabitable {
    @JsonSerialize(using = CustomPlanElementSerializer.class)
    protected EntryPoint entryPoint;
    @JsonSerialize(using = CustomPlanElementSerializer.class)
    protected Plan parentPlan;
    @JsonSerialize(using = CustomArrayFileReferenceSerializer.class)
    protected ArrayList<AbstractPlan> plans;
    @JsonSerialize(using = CustomArraySerializer.class)
    @JsonDeserialize(using = CustomArrayDeserializer.class)
    protected ArrayList<Parametrisation> parametrisations;
    @JsonSerialize(using = CustomArraySerializer.class)
    @JsonDeserialize(using = CustomArrayDeserializer.class)
    protected ArrayList<Transition> inTransitions;
    @JsonSerialize(using = CustomArraySerializer.class)
    @JsonDeserialize(using = CustomArrayDeserializer.class)
    protected ArrayList<Transition> outTransitions;

    public State ()
    {
        plans = new ArrayList<>();
        parametrisations = new ArrayList<>();
        inTransitions = new ArrayList<>();
        outTransitions = new ArrayList<>();
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

    public ArrayList<AbstractPlan> getPlans() {
        return plans;
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
    }

    public void removeAbstractPlan(AbstractPlan abstractPlan) {
        plans.remove(abstractPlan);

        for (Parametrisation param : parametrisations) {
            if (param.getSubPlan().getId() == abstractPlan.getId()) {
                parametrisations.remove(param);
            }
        }
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
