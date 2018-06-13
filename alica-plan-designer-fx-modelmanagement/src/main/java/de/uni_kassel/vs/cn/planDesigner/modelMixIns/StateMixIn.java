package de.uni_kassel.vs.cn.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.deserialization.ArrayDeserializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.CustomArraySerializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.CustomPlanElementSerializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.FileArraySerializer;

import java.util.ArrayList;

public abstract class StateMixIn {
    @JsonSerialize(using = CustomPlanElementSerializer.class)
    protected EntryPoint entryPoint;
    @JsonSerialize(using = CustomPlanElementSerializer.class)
    protected Plan parentPlan;
    @JsonSerialize(using = FileArraySerializer.class)
    @JsonDeserialize(using = ArrayDeserializer.class)
    protected ArrayList<AbstractPlan> plans;
    @JsonSerialize(using = CustomArraySerializer.class)
    @JsonDeserialize(using = ArrayDeserializer.class)
    protected ArrayList<Parametrisation> parametrisations;
    @JsonSerialize(using = CustomArraySerializer.class)
    @JsonDeserialize(using = ArrayDeserializer.class)
    protected ArrayList<Transition> inTransitions;
    @JsonSerialize(using = CustomArraySerializer.class)
    @JsonDeserialize(using = ArrayDeserializer.class)
    protected ArrayList<Transition> outTransitions;
}
