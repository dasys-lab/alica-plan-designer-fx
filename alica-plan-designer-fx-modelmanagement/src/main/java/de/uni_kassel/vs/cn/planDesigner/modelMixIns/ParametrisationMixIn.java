package de.uni_kassel.vs.cn.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.serialization.ExternalRefSerializer;

public abstract class ParametrisationMixIn {
    @JsonSerialize(using = ExternalRefSerializer.class)
    protected AbstractPlan subPlan;
}
