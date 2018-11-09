package de.unikassel.vs.alica.planDesigner.modelMixIns;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.deserialization.FileArrayDeserializer;
import de.unikassel.vs.alica.planDesigner.deserialization.PlanDeserializer;
import de.unikassel.vs.alica.planDesigner.serialization.ExternalRefArraySerializer;
import de.unikassel.vs.alica.planDesigner.serialization.InternalRefArraySerializer;
import de.unikassel.vs.alica.planDesigner.serialization.InternalRefSerializer;

import java.util.ArrayList;

@JsonTypeInfo(  use = JsonTypeInfo.Id.NAME,
                defaultImpl = State.class,
                property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = State.class),
        @JsonSubTypes.Type(value = TerminalState.class),
})

public abstract class StateMixIn {
    @JsonSerialize(using = InternalRefSerializer.class)
    protected EntryPoint entryPoint;
    @JsonSerialize(using = InternalRefSerializer.class)
    @JsonDeserialize(using = PlanDeserializer.class)
    protected Plan parentPlan;
    @JsonSerialize(using = ExternalRefArraySerializer.class)
    @JsonDeserialize(using = FileArrayDeserializer.class)
    protected ArrayList<AbstractPlan> plans;
    @JsonSerialize(using = InternalRefArraySerializer.class)
    protected ArrayList<Parametrisation> parametrisations;
    @JsonSerialize(using = InternalRefArraySerializer.class)
    protected ArrayList<Transition> inTransitions;
    @JsonSerialize(using = InternalRefArraySerializer.class)
    protected ArrayList<Transition> outTransitions;
}
