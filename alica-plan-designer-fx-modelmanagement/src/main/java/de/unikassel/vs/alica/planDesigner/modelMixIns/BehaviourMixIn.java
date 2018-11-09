package de.unikassel.vs.alica.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.unikassel.vs.alica.planDesigner.alicamodel.PreCondition;
import de.unikassel.vs.alica.planDesigner.serialization.InternalRefArraySerializer;

import java.util.ArrayList;

public abstract class BehaviourMixIn {
    @JsonSerialize(using = InternalRefArraySerializer.class)
    protected ArrayList<PreCondition> preConditions;
    @JsonSerialize(using = InternalRefArraySerializer.class)
    protected ArrayList<PreCondition> runtimeConditions;
    @JsonSerialize(using = InternalRefArraySerializer.class)
    protected ArrayList<PreCondition> postConditions;
}
