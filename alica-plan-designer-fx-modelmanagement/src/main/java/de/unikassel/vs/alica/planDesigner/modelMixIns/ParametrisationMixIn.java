package de.unikassel.vs.alica.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.unikassel.vs.alica.planDesigner.alicamodel.AbstractPlan;
import de.unikassel.vs.alica.planDesigner.serialization.ExternalRefSerializer;

public abstract class ParametrisationMixIn {
    @JsonSerialize(using = ExternalRefSerializer.class)
    protected AbstractPlan subPlan;
}
