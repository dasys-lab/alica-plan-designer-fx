package de.uni_kassel.vs.cn.planDesigner.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ValueNode;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Task;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ParsedModelReferences;

import java.io.IOException;

public class EntryPointTaskDeserializer extends StdDeserializer<Task> {

    public EntryPointTaskDeserializer() {
        this(null);
    }

    public EntryPointTaskDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Task deserialize(
            JsonParser jsonparser,
            DeserializationContext context)
            throws IOException, JsonProcessingException {
        TreeNode tree = jsonparser.getCodec().readTree(jsonparser);
        String taskString = ((ValueNode) tree).asText();
        int idIndex = taskString.indexOf('#');
        taskString = taskString.substring(idIndex + 1);
        Task task = new Task(Long.parseLong(taskString));
        ParsedModelReferences.getInstance().addIncompleteTask(task);
        return task;
    }
}
