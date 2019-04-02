package de.unikassel.vs.alica.planDesigner.modelMixIns;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.deserialization.ExternalRefDeserializer;
import de.unikassel.vs.alica.planDesigner.serialization.ExternalRefSerializer;
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
    protected Plan parentPlan;
    @JsonSerialize(contentUsing = ExternalRefSerializer.class)
    @JsonDeserialize(contentUsing = ExternalRefDeserializer.class)
    protected ArrayList<AbstractPlan> abstractPlans;
    protected ArrayList<VariableBinding> variableBindings;
    @JsonSerialize(contentUsing = InternalRefSerializer.class)
    protected ArrayList<Transition> inTransitions;
    @JsonSerialize(contentUsing = InternalRefSerializer.class)
    protected ArrayList<Transition> outTransitions;
}
