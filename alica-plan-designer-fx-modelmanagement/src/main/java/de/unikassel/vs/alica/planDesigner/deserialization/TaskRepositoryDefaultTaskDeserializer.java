package de.unikassel.vs.alica.planDesigner.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.LongNode;
import de.unikassel.vs.alica.planDesigner.alicamodel.Task;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ParsedModelReferences;

import java.io.IOException;

public class TaskRepositoryDefaultTaskDeserializer extends StdDeserializer<Task> {

    public TaskRepositoryDefaultTaskDeserializer() {
        this(null);
    }

    public TaskRepositoryDefaultTaskDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Task deserialize(
            JsonParser jsonparser,
            DeserializationContext context)
            throws IOException, JsonProcessingException {
        TreeNode tree = jsonparser.getCodec().readTree(jsonparser);
        long id = (Long) ((LongNode) tree).numberValue();
        ParsedModelReferences.getInstance().setDefaultTaskId(id);
        return null;
    }
}
