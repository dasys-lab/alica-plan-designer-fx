package de.unikassel.vs.alica.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.unikassel.vs.alica.planDesigner.alicamodel.Task;
import de.unikassel.vs.alica.planDesigner.deserialization.TaskRepositoryDefaultTaskDeserializer;
import de.unikassel.vs.alica.planDesigner.deserialization.TaskRepositoryTasksDeserializer;
import javafx.collections.ObservableList;

public abstract class TaskRepositoryMixIn {
    @JsonDeserialize(using = TaskRepositoryTasksDeserializer.class)
    protected ObservableList<Task> tasks;
    @JsonDeserialize(using = TaskRepositoryDefaultTaskDeserializer.class)
    protected Task defaultTask;
}
