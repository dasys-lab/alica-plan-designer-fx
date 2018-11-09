package de.unikassel.vs.alica.planDesigner.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.LongNode;
import de.unikassel.vs.alica.planDesigner.alicamodel.Synchronization;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ParsedModelReferences;

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
        ParsedModelReferences.getInstance().addIncompleteSynchronizationInTransition(synchronization.getId());
        return synchronization;
        }
}
