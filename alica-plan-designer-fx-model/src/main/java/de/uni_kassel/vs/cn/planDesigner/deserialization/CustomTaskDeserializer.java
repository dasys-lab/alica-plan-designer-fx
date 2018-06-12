package de.uni_kassel.vs.cn.planDesigner.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Task;

import java.io.IOException;

public class CustomTaskDeserializer extends StdDeserializer<Task> {
    public CustomTaskDeserializer() {
        this(null);
    }

    public CustomTaskDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Task deserialize(
            JsonParser jsonparser,
            DeserializationContext context)
            throws IOException, JsonProcessingException {
        System.out.println("deserialize called");
        System.out.println(context.getAttribute("id"));
        return new Task();
    }
}
