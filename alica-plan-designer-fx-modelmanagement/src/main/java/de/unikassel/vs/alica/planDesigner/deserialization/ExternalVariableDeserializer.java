package de.unikassel.vs.alica.planDesigner.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ValueNode;
import de.unikassel.vs.alica.planDesigner.alicamodel.Variable;

import java.io.IOException;


public class ExternalVariableDeserializer extends StdDeserializer<Variable> {

    public ExternalVariableDeserializer() {
        this(null);
    }

    public ExternalVariableDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Variable deserialize(
            JsonParser jsonparser,
            DeserializationContext context)
            throws IOException, JsonProcessingException {
        TreeNode tree = jsonparser.getCodec().readTree(jsonparser);
        String planElementString = ((ValueNode) tree).asText().trim();
        int idIndex = planElementString.indexOf('#');
        planElementString = planElementString.substring(idIndex + 1);
        Variable var = new Variable(Long.parseLong(planElementString));
        return var;
    }
}
