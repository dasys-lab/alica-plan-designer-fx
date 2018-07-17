package de.uni_kassel.vs.cn.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.deserialization.ArrayDeserializer;
import de.uni_kassel.vs.cn.planDesigner.deserialization.FileArrayDeserializer;
import de.uni_kassel.vs.cn.planDesigner.deserialization.PlanDeserializer;
import de.uni_kassel.vs.cn.planDesigner.deserialization.SimpleLongPropertyDeserializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.InternRefArraySerializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.InternRefSerializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.ExternRefArraySerializer;
import javafx.beans.property.SimpleLongProperty;

import java.util.ArrayList;

public abstract class StateMixIn {
    @JsonSerialize(using = InternRefSerializer.class)
    protected EntryPoint entryPoint;
    @JsonSerialize(using = InternRefSerializer.class)
    @JsonDeserialize(using = PlanDeserializer.class)
    protected Plan parentPlan;
    @JsonSerialize(using = ExternRefArraySerializer.class)
    @JsonDeserialize(using = FileArrayDeserializer.class)
    protected ArrayList<AbstractPlan> plans;
    @JsonSerialize(using = InternRefArraySerializer.class)
    @JsonDeserialize(using = ArrayDeserializer.class)
    protected ArrayList<Parametrisation> parametrisations;
    @JsonSerialize(using = InternRefArraySerializer.class)
    @JsonDeserialize(using = ArrayDeserializer.class)
    protected ArrayList<Transition> inTransitions;
    @JsonSerialize(using = InternRefArraySerializer.class)
    @JsonDeserialize(using = ArrayDeserializer.class)
    protected ArrayList<Transition> outTransitions;
}
