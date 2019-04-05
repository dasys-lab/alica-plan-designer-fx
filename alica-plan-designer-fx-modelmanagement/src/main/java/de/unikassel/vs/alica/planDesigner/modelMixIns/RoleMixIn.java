package de.unikassel.vs.alica.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.unikassel.vs.alica.planDesigner.alicamodel.RoleSet;
import de.unikassel.vs.alica.planDesigner.alicamodel.Task;
import de.unikassel.vs.alica.planDesigner.deserialization.RoleSetRoles2Deserializer;
import de.unikassel.vs.alica.planDesigner.deserialization.RoleSetRolesDeserializer;
import de.unikassel.vs.alica.planDesigner.serialization.InternalRefKeySerializer;
import de.unikassel.vs.alica.planDesigner.serialization.InternalRefSerializer;

import java.util.HashMap;

public abstract class RoleMixIn {
    @JsonSerialize(using = InternalRefSerializer.class)
    protected RoleSet roleSet;

    @JsonSerialize(keyUsing = InternalRefSerializer.class)
    @JsonDeserialize(keyUsing = RoleSetRolesDeserializer.class)
    protected HashMap<Task, Float> taskPriorities;

    @JsonSerialize(using = InternalRefKeySerializer.class)
    @JsonDeserialize(using = RoleSetRoles2Deserializer.class)
    protected Task task;
}
