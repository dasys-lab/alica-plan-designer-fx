package de.unikassel.vs.alica.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.unikassel.vs.alica.planDesigner.alicamodel.Transition;
import de.unikassel.vs.alica.planDesigner.serialization.InternalRefSerializer;

public abstract class BendPointMixIn {
    @JsonSerialize(using = InternalRefSerializer.class)
    protected Transition transition;
}
