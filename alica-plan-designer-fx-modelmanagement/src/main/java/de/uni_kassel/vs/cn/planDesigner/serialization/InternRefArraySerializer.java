package de.uni_kassel.vs.cn.planDesigner.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;

import java.io.IOException;
import java.util.ArrayList;

public class InternRefArraySerializer extends StdSerializer<ArrayList<PlanElement>> {

    public InternRefArraySerializer() {
        this(null);
    }

    public InternRefArraySerializer(Class<ArrayList<PlanElement>> t) {
        super(t);
    }

    @Override
    public void serialize(ArrayList<PlanElement> planElements, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        long[] ids = new long[planElements.size()];
        int i = 0;
        for (PlanElement element: planElements) {
            ids[i++] = element.getId();
        }
        jsonGenerator.writeArray(ids, 0, ids.length);

    }
}
