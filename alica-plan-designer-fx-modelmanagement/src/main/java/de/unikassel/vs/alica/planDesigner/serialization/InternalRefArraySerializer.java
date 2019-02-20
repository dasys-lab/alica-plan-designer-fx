package de.unikassel.vs.alica.planDesigner.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InternalRefArraySerializer extends StdSerializer<List<PlanElement>> {

    public InternalRefArraySerializer() {
        this(null);
    }

    public InternalRefArraySerializer(Class<List<PlanElement>> t) {
        super(t);
    }

    @Override
    public void serialize(List<PlanElement> planElements, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        long[] ids = new long[planElements.size()];
        int i = 0;
        for (PlanElement element: planElements) {
            ids[i++] = element.getId();
        }
        jsonGenerator.writeArray(ids, 0, ids.length);

    }
}
