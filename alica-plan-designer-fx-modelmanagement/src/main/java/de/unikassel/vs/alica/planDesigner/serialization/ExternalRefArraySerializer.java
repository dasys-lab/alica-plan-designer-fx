package de.unikassel.vs.alica.planDesigner.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Extensions;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class ExternalRefArraySerializer extends StdSerializer<List<AbstractPlan>> {

    public ExternalRefArraySerializer() {
        this(null);
    }

    public ExternalRefArraySerializer(Class<List<AbstractPlan>> t) {
        super(t);
    }

    @Override
    public void serialize(List<AbstractPlan> planElements, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String result = "";
        for (AbstractPlan element : planElements) {
            if(element instanceof Plan) {
                result +=  Paths.get(element.getRelativeDirectory(), element.getName() + "." + Extensions.PLAN + "#" + element.getId()).toString() + ", ";
            } else if(element instanceof Behaviour) {
                result +=  Paths.get(element.getRelativeDirectory(), element.getName() + "." + Extensions.BEHAVIOUR + "#" + element.getId()).toString() + ", ";
            } else if(element instanceof PlanType) {
                result +=  Paths.get(element.getRelativeDirectory(), element.getName() + "." + Extensions.PLANTYPE + "#" + element.getId()).toString() + ", ";
            } else if(element instanceof Configuration) {
                result += Paths.get(element.getRelativeDirectory(), ((Configuration) element).getBehaviour().getName()
                    + "." + Extensions.BEHAVIOUR + "#" + element.getId()).toString() + ", ";
            }
        }
        jsonGenerator.writeString(result);
    }
}
