package de.uni_kassel.vs.cn.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.State;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Task;
import de.uni_kassel.vs.cn.planDesigner.deserialization.EntryPointTaskDeserializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.ExternalRefSerializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.InternalRefSerializer;

public abstract class EntryPointMixIn {
    @JsonSerialize(using = ExternalRefSerializer.class)
    @JsonDeserialize(using = EntryPointTaskDeserializer.class)
    protected Task task;
    @JsonSerialize(using = InternalRefSerializer.class)
    protected State state;
    @JsonSerialize(using = InternalRefSerializer.class)
    protected Plan plan;
}
