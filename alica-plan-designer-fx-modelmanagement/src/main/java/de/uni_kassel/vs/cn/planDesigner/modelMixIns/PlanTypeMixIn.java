package de.uni_kassel.vs.cn.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.deserialization.PlanTypePlansDeserializer;
import de.uni_kassel.vs.cn.planDesigner.serialization.PlanTypePlansSerializer;

import java.util.ArrayList;

public abstract class PlanTypeMixIn {
    @JsonSerialize(using = PlanTypePlansSerializer.class)
    @JsonDeserialize(using = PlanTypePlansDeserializer.class)
    ArrayList<Plan> plans;
}
