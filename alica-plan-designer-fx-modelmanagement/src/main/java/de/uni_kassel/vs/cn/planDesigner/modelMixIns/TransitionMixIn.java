package de.uni_kassel.vs.cn.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PreCondition;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.State;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronization;
import de.uni_kassel.vs.cn.planDesigner.serialization.CustomPlanElementSerializer;

public abstract class TransitionMixIn {
    @JsonSerialize(using = CustomPlanElementSerializer.class)
    protected State inState;
    @JsonSerialize(using = CustomPlanElementSerializer.class)
    protected State outState;
    protected PreCondition preCondition;
    @JsonSerialize(using = CustomPlanElementSerializer.class)
    protected Synchronization synchronization;
}
