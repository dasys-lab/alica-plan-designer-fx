package de.uni_kassel.vs.cn.planDesigner.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.TaskRepository;

import java.io.IOException;
import java.nio.file.Paths;

public class CustomTaskRepositorySerializer extends StdSerializer<TaskRepository> {

    public CustomTaskRepositorySerializer() {
        this(null);
    }

    public CustomTaskRepositorySerializer(Class<TaskRepository> t) {
        super(t);
    }

    @Override
    public void serialize(TaskRepository planElement, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(planElement.getId());
    }
}
