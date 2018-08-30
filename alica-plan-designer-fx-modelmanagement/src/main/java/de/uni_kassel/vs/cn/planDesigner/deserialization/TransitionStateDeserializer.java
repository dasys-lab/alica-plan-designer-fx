package de.uni_kassel.vs.cn.planDesigner.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.LongNode;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.State;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ParsedModelReferences;

import java.io.IOException;


public class TransitionStateDeserializer extends StdDeserializer<State> {

public TransitionStateDeserializer() {
        this(null);
        }

public TransitionStateDeserializer(Class<?> vc) {super(vc);}

@Override
public State deserialize(
        JsonParser jsonparser,
        DeserializationContext context)
        throws IOException, JsonProcessingException {
        TreeNode tree = jsonparser.getCodec().readTree(jsonparser);
        State state = new State(((LongNode)tree).longValue());
        ParsedModelReferences.getInstance().addIncompleteStateInTransition(state.getId());
        return state;
        }
}
