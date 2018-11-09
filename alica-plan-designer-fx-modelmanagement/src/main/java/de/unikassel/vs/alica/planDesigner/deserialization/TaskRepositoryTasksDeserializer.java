package de.unikassel.vs.alica.planDesigner.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import de.unikassel.vs.alica.planDesigner.alicamodel.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class TaskRepositoryTasksDeserializer extends StdDeserializer<ObservableList<Task>> {

    public TaskRepositoryTasksDeserializer() {
        this(null);
    }

    public TaskRepositoryTasksDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ObservableList<Task> deserialize(
            JsonParser jsonparser,
            DeserializationContext context)
            throws IOException, JsonProcessingException {
        TreeNode tree = jsonparser.getCodec().readTree(jsonparser);
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        for(int i = 0; i < tree.size(); i++) {
            TreeNode currentTreeNode = tree.get(i);
            Task task = new Task(((LongNode)currentTreeNode.get("id")).longValue());
            task.setName(((ValueNode)currentTreeNode.get("name")).textValue());
            task.setComment(((ValueNode)currentTreeNode.get("comment")).textValue());
            tasks.add(task);
        }
        return tasks;
    }
}
