package de.uni_kassel.vs.cn.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.State;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Task;
import de.uni_kassel.vs.cn.planDesigner.deserialization.EntryPointTaskDeserializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.ExternRefSerializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.InternRefSerializer;

public abstract class EntryPointMixIn {
    @JsonSerialize(using = ExternRefSerializer.class)
    @JsonDeserialize(using = EntryPointTaskDeserializer.class)
    protected Task task;
    @JsonSerialize(using = InternRefSerializer.class)
    protected State state;
    @JsonSerialize(using = InternRefSerializer.class)
    protected Plan plan;
}
