package de.uni_kassel.vs.cn.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.serialization.ExternRefSerializer;

public abstract class AnnotatedPlanMixIn {
    @JsonSerialize(using = ExternRefSerializer.class)
    //@JsonDeserialize(using = CustomFileReferenceDeserializer.class)
    Plan plan;
}
