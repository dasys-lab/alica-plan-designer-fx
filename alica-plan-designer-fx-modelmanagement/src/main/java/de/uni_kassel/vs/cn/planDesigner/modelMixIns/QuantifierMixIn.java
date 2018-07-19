package de.uni_kassel.vs.cn.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.serialization.InternalRefSerializer;

public abstract class QuantifierMixIn {
    @JsonSerialize(using = InternalRefSerializer.class)
    protected PlanElement scope;
}
