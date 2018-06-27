package de.uni_kassel.vs.cn.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.deserialization.FileArrayDeserializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.FileArraySerializer;

import java.util.ArrayList;

public abstract class PlanTypeMixIn {
    @JsonSerialize(using = FileArraySerializer.class)
    @JsonDeserialize(using = FileArrayDeserializer.class)
    ArrayList<Plan> plans;
}
