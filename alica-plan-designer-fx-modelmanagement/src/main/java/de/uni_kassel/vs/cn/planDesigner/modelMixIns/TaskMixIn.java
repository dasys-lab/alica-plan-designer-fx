package de.uni_kassel.vs.cn.planDesigner.modelMixIns;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskRepository;
import de.uni_kassel.vs.cn.planDesigner.serialization.InternRefSerializer;

public abstract class TaskMixIn {
    @JsonSerialize(using = InternRefSerializer.class)
    TaskRepository taskRepository;
}
