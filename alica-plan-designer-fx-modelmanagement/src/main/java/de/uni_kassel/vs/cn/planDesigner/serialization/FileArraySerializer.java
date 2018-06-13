package de.uni_kassel.vs.cn.planDesigner.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileArraySerializer extends StdSerializer<ArrayList<AbstractPlan>> {

    public FileArraySerializer() {
        this(null);
    }

    public FileArraySerializer(Class<ArrayList<AbstractPlan>> t) {
        super(t);
    }

    @Override
    public void serialize(ArrayList<AbstractPlan> planElements, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String result = "";
        for (AbstractPlan element : planElements) {
            if(element instanceof Plan) {
                result +=  Paths.get(element.getRelativeDirectory(), element.getName() + ".pml#" + element.getId()).toString() + ", ";
            } else if(element instanceof Behaviour) {
                result +=  Paths.get(element.getRelativeDirectory(), element.getName() + ".beh#" + element.getId()).toString() + ", ";
            } else if(element instanceof PlanType) {
                result +=  Paths.get(element.getRelativeDirectory(), element.getName() + ".pty#" + element.getId()).toString() + ", ";
            }
        }
        jsonGenerator.writeString(result);
    }
}
