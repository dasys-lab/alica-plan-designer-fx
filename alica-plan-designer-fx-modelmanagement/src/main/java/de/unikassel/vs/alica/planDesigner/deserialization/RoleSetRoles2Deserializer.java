package de.unikassel.vs.alica.planDesigner.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;
import de.unikassel.vs.alica.planDesigner.alicamodel.Task;

import java.io.IOException;

public class RoleSetRoles2Deserializer extends StdDeserializer<Task> {


    protected RoleSetRoles2Deserializer(StdDeserializer<?> src) {
        super(src);
    }

    @Override
    public Task deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        System.out.println("RSR2D: deserializeKey not implemented");
        return null;
    }
}
