package de.uni_kassel.vs.cn.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.IInhabitable;
import de.uni_kassel.vs.cn.planDesigner.serialization.CustomPlanElementSerializer;

public abstract class QuantifierMixIn {
    @JsonSerialize(using = CustomPlanElementSerializer.class)
    protected IInhabitable scope;
}
