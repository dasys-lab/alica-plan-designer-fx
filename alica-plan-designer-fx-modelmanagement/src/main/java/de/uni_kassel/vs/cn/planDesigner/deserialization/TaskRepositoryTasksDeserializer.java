package de.uni_kassel.vs.cn.planDesigner.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class TaskRepositoryTasksDeserializer extends StdDeserializer<ArrayList<Task>> {

    public TaskRepositoryTasksDeserializer() {
        this(null);
    }

    public TaskRepositoryTasksDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ArrayList<Task> deserialize(
            JsonParser jsonparser,
            DeserializationContext context)
            throws IOException, JsonProcessingException {
        TreeNode tree = jsonparser.getCodec().readTree(jsonparser);
        ArrayList<Task> tasks = new ArrayList<Task>();
        for(int i = 0; i < tree.size(); i++) {
            TreeNode currentTreeNode = tree.get(i);
            Task task = new Task(Long.parseLong(currentTreeNode.get("id").toString()));
            task.setName(currentTreeNode.get("name").toString().replaceAll("\"", ""));
            task.setComment(currentTreeNode.get("comment").toString());
            tasks.add(task);
        }
        return tasks;
    }
}
