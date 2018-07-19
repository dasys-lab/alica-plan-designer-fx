package de.uni_kassel.vs.cn.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PreCondition;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.State;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronization;
import de.uni_kassel.vs.cn.planDesigner.serialization.InternalRefSerializer;

public abstract class TransitionMixIn {
    @JsonSerialize(using = InternalRefSerializer.class)
    protected State inState;
    @JsonSerialize(using = InternalRefSerializer.class)
    protected State outState;
    protected PreCondition preCondition;
    @JsonSerialize(using = InternalRefSerializer.class)
    protected Synchronization synchronization;
}
