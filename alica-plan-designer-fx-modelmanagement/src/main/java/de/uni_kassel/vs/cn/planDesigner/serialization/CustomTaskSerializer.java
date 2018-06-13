package de.uni_kassel.vs.cn.planDesigner.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.SerializablePlanElement;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Task;

import java.io.IOException;
import java.nio.file.Paths;

public class CustomTaskSerializer extends StdSerializer<Task> {

    public CustomTaskSerializer() {
        this(null);
    }

    public CustomTaskSerializer(Class<Task> t) {
        super(t);
    }

    @Override
    public void serialize(Task planElement, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(Paths.get(planElement.getTaskRepository().getRelativeDirectory(), planElement.getTaskRepository().getName() + ".tsk#" + planElement.getId()).toString());
    }
}
