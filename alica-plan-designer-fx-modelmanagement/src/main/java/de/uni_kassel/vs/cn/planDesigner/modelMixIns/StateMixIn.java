package de.uni_kassel.vs.cn.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.deserialization.FileArrayDeserializer;
import de.uni_kassel.vs.cn.planDesigner.deserialization.PlanDeserializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.InternalRefArraySerializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.InternalRefSerializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.ExternalRefArraySerializer;

import java.util.ArrayList;

public abstract class StateMixIn {
    @JsonSerialize(using = InternalRefSerializer.class)
    protected EntryPoint entryPoint;
    @JsonSerialize(using = InternalRefSerializer.class)
    @JsonDeserialize(using = PlanDeserializer.class)
    protected Plan parentPlan;
    @JsonSerialize(using = ExternalRefArraySerializer.class)
    @JsonDeserialize(using = FileArrayDeserializer.class)
    protected ArrayList<AbstractPlan> plans;
    @JsonSerialize(using = InternalRefArraySerializer.class)
    protected ArrayList<Parametrisation> parametrisations;
    @JsonSerialize(using = InternalRefArraySerializer.class)
    protected ArrayList<Transition> inTransitions;
    @JsonSerialize(using = InternalRefArraySerializer.class)
    protected ArrayList<Transition> outTransitions;
}
