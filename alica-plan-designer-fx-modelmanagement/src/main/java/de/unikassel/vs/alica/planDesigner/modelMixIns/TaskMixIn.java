package de.unikassel.vs.alica.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.unikassel.vs.alica.planDesigner.alicamodel.TaskRepository;
import de.unikassel.vs.alica.planDesigner.serialization.InternalRefSerializer;

public abstract class TaskMixIn {
    @JsonSerialize(using = InternalRefSerializer.class)
    TaskRepository taskRepository;
}
