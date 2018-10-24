package de.uni_kassel.vs.cn.planDesigner.deserialization;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;

import java.io.IOException;

public class PlanElementKeyDeserializer extends KeyDeserializer {
    @Override
    public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException {
        //Create an empty PlanElement, that only contains an id
        return new PlanElement(Long.parseLong(key));
    }
}
