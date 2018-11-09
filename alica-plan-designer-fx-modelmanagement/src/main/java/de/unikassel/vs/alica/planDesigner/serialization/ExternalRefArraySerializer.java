package de.unikassel.vs.alica.planDesigner.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.unikassel.vs.alica.planDesigner.alicamodel.AbstractPlan;
import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.PlanType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.FileSystemUtil;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ExternalRefArraySerializer extends StdSerializer<ArrayList<AbstractPlan>> {

    public ExternalRefArraySerializer() {
        this(null);
    }

    public ExternalRefArraySerializer(Class<ArrayList<AbstractPlan>> t) {
        super(t);
    }

    @Override
    public void serialize(ArrayList<AbstractPlan> planElements, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String result = "";
        for (AbstractPlan element : planElements) {
            if(element instanceof Plan) {
                result +=  Paths.get(element.getRelativeDirectory(), element.getName() + "." + FileSystemUtil.PLAN_ENDING + "#" + element.getId()).toString() + ", ";
            } else if(element instanceof Behaviour) {
                result +=  Paths.get(element.getRelativeDirectory(), element.getName() + "." + FileSystemUtil.BEHAVIOUR_ENDING + "#" + element.getId()).toString() + ", ";
            } else if(element instanceof PlanType) {
                result +=  Paths.get(element.getRelativeDirectory(), element.getName() + "." + FileSystemUtil.PLANTYPE_ENDING + "#" + element.getId()).toString() + ", ";
            }
        }
        jsonGenerator.writeString(result);
    }
}
