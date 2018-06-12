package de.uni_kassel.vs.cn.planDesigner.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;

import java.io.IOException;
import java.util.ArrayList;

public class CustomArrayDeserializer extends StdDeserializer<ArrayList<PlanElement>> {

    public CustomArrayDeserializer() {
        this(null);
    }

    public CustomArrayDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ArrayList<PlanElement> deserialize(
            JsonParser jsonparser,
            DeserializationContext context)
            throws IOException, JsonProcessingException {
        System.out.println("deserialize called");
        System.out.println(jsonparser.currentName());
        return new ArrayList<PlanElement>();
    }
}
