package de.unikassel.vs.alica.planDesigner.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;
import de.unikassel.vs.alica.planDesigner.alicamodel.Task;

import java.io.IOException;

public class RoleTaskDeserializer extends KeyDeserializer {


//    @Override
//    public Task deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
//        System.out.println("RTDeS: deserializeKey not implemented");
//        return null;
//    }

    @Override
    public Object deserializeKey(String s, DeserializationContext deserializationContext) throws IOException {
        System.err.println("RTDeS: deserializeKey not implemented");
        return null;
    }
}
