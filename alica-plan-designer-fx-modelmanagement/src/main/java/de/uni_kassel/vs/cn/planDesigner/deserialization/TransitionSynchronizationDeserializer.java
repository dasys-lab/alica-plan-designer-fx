package de.uni_kassel.vs.cn.planDesigner.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.LongNode;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronization;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ParsedModelReferences;

import java.io.IOException;


public class TransitionSynchronizationDeserializer extends StdDeserializer<Synchronization> {

public TransitionSynchronizationDeserializer() {
        this(null);
        }

public TransitionSynchronizationDeserializer(Class<?> vc) {super(vc);}

@Override
public Synchronization deserialize(
        JsonParser jsonparser,
        DeserializationContext context)
        throws IOException, JsonProcessingException {
        TreeNode tree = jsonparser.getCodec().readTree(jsonparser);
        Synchronization synchronization = new Synchronization(((LongNode)tree).longValue());
        ParsedModelReferences.getInstance().addIncompleteSynchronizationsInTransitions(synchronization.getId());
        return synchronization;
        }
}
