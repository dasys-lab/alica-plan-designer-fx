package de.unikassel.vs.alica.planDesigner.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import de.unikassel.vs.alica.planDesigner.alicamodel.Role;
import de.unikassel.vs.alica.planDesigner.alicamodel.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class RoleTaskPriorityDeserializer extends JsonDeserializer {

    @Override
    public HashMap<Task, Float> deserialize (JsonParser jsonparser, DeserializationContext context) throws IOException, JsonProcessingException {
        HashMap<Task, Float> taskProperties = new HashMap();
        HashMap taskPropertyObjects = jsonparser.getCodec().readValue(jsonparser, HashMap.class);

        taskPropertyObjects.forEach((t, p) -> {
            String taskString = (String) t;
            int idIndex = taskString.indexOf('#');
            taskString = taskString.substring(idIndex + 1);
            Task task = new Task(Long.parseLong(taskString));
            taskProperties.put(task, Float.valueOf(String.valueOf(p)));
        });

        return taskProperties;
    }
}
