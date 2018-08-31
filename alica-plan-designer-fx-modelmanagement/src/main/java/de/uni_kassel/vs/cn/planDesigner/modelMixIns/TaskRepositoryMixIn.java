package de.uni_kassel.vs.cn.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Task;
import de.uni_kassel.vs.cn.planDesigner.deserialization.TaskRepositoryDefaultTaskDeserializer;
import de.uni_kassel.vs.cn.planDesigner.deserialization.TaskRepositoryTasksDeserializer;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public abstract class TaskRepositoryMixIn {
    @JsonDeserialize(using = TaskRepositoryTasksDeserializer.class)
    protected ObservableList<Task> tasks;
    @JsonDeserialize(using = TaskRepositoryDefaultTaskDeserializer.class)
    protected Task defaultTask;
}
