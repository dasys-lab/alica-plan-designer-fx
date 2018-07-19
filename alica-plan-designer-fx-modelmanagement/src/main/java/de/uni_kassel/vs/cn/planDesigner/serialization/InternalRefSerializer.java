package de.uni_kassel.vs.cn.planDesigner.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;

import java.io.IOException;

public class InternalRefSerializer extends StdSerializer<PlanElement> {

    public InternalRefSerializer() {
        this(null);
    }

    public InternalRefSerializer(Class<PlanElement> t) {
        super(t);
    }

    @Override
    public void serialize(PlanElement planElement, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(planElement.getId());
    }
}
