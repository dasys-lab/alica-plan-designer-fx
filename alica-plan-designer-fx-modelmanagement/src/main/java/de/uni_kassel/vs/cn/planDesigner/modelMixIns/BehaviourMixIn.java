package de.uni_kassel.vs.cn.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PreCondition;
import de.uni_kassel.vs.cn.planDesigner.deserialization.ArrayDeserializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.InternalRefArraySerializer;

import java.util.ArrayList;

public abstract class BehaviourMixIn {
    @JsonSerialize(using = InternalRefArraySerializer.class)
    @JsonDeserialize(using = ArrayDeserializer.class)
    protected ArrayList<PreCondition> preConditions;
    @JsonSerialize(using = InternalRefArraySerializer.class)
    @JsonDeserialize(using = ArrayDeserializer.class)
    protected ArrayList<PreCondition> runtimeConditions;
    @JsonSerialize(using = InternalRefArraySerializer.class)
    @JsonDeserialize(using = ArrayDeserializer.class)
    protected ArrayList<PreCondition> postConditions;
}
