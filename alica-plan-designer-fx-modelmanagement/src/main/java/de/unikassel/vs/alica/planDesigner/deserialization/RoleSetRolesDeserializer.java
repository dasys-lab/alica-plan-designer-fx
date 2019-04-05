package de.unikassel.vs.alica.planDesigner.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import de.unikassel.vs.alica.planDesigner.alicamodel.Role;
import de.unikassel.vs.alica.planDesigner.alicamodel.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class RoleSetRolesDeserializer extends KeyDeserializer {

    @Override
    public Object deserializeKey(String s, DeserializationContext deserializationContext) throws IOException {
        System.out.println("RSRD: deserializeKey not implemented");
        return null;
//        TreeNode tree = jsonparser.getCodec().readTree(jsonparser);
//        ObservableList<Role> roles = FXCollections.observableArrayList();
//        for(int i = 0; i < tree.size(); i++) {
//            TreeNode currentTreeNode = tree.get(i);
//            Role role = new Role(((LongNode)currentTreeNode.get("id")).longValue());
//            role.setName(((ValueNode)currentTreeNode.get("name")).textValue());
//            role.setComment(((ValueNode)currentTreeNode.get("comment")).textValue());
//            roles.add(role);
//        }
//        return roles;
    }

//    public RoleSetRolesDeserializer(Class<?> vc) {
//        super(vc);
//    }

//    @Override
//    public ObservableList<Role> deserialize(
//            JsonParser jsonparser,
//            DeserializationContext context)
//            throws IOException, JsonProcessingException {
//        TreeNode tree = jsonparser.getCodec().readTree(jsonparser);
//        ObservableList<Role> roles = FXCollections.observableArrayList();
//        for(int i = 0; i < tree.size(); i++) {
//            TreeNode currentTreeNode = tree.get(i);
//            Role role = new Role(((LongNode)currentTreeNode.get("id")).longValue());
//            role.setName(((ValueNode)currentTreeNode.get("name")).textValue());
//            role.setComment(((ValueNode)currentTreeNode.get("comment")).textValue());
//            roles.add(role);
//        }
//        return roles;
//    }
}
