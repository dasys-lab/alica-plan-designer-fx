package de.uni_kassel.vs.cn.planDesigner.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Task;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ParsedModelReference;

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
        String taskString = tree.toString();
        int idIndex = taskString.indexOf('#');
        taskString = taskString.substring(idIndex + 1);
        taskString.replace("\"", "");
        Task task = new Task(Long.parseLong(taskString));
        ParsedModelReference.getInstance().addIncompleteTask(task);
        return task;
    }
}
