package de.unikassel.vs.alica.planDesigner.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import de.unikassel.vs.alica.planDesigner.alicamodel.Role;
import de.unikassel.vs.alica.planDesigner.alicamodel.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.io.IOException;
import java.util.HashMap;

public class RoleDeserializer extends JsonDeserializer {

    @Override
    public HashMap<Task, Float> deserialize (
            JsonParser jsonparser,
            DeserializationContext context)
            throws IOException, JsonProcessingException {
        TreeNode tree = jsonparser.getCodec().readTree(jsonparser);

        HashMap<Task, Float> taskProperties = new HashMap();
        for(int i = 0; i < tree.size(); i++) {
            TreeNode currentTreeNode = tree.get(i);
//            Role role = new Role(((LongNode)currentTreeNode.get("id")).longValue());
//            role.setName(((ValueNode)currentTreeNode.get("name")).textValue());
//            role.setComment(((ValueNode)currentTreeNode.get("comment")).textValue());
//            roles.add(role);
        }
        return taskProperties;
    }
}
