package de.uni_kassel.vs.cn.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.deserialization.PlanDeserializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.ExternalRefSerializer;

public abstract class PlanModelVisualizationObjectMixIn {

    @JsonSerialize(using = ExternalRefSerializer.class)
    @JsonDeserialize(using = PlanDeserializer.class)
    public abstract Plan getPlan();
}
