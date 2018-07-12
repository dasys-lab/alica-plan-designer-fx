package de.uni_kassel.vs.cn.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Transition;
import de.uni_kassel.vs.cn.planDesigner.serialization.InternRefSerializer;

import java.util.ArrayList;

public abstract class SynchronizationMixIn {
    @JsonSerialize(using = InternRefSerializer.class)
    protected ArrayList<Transition> synchedTransitions;
}
