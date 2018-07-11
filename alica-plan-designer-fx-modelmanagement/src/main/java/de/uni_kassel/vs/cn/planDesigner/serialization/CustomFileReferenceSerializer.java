package de.uni_kassel.vs.cn.planDesigner.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CustomFileReferenceSerializer extends StdSerializer<AbstractPlan> {

    public CustomFileReferenceSerializer() {
        this(null);
    }

    public CustomFileReferenceSerializer(Class<AbstractPlan> t) {
        super(t);
    }

    @Override
    public void serialize(AbstractPlan planElement, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(planElement instanceof Plan) {
            jsonGenerator.writeString(Paths.get(planElement.getRelativeDirectory(), planElement.getName() + ".pml#" + planElement.getId()).toString());
        } else if(planElement instanceof Behaviour) {
            jsonGenerator.writeString(Paths.get(planElement.getRelativeDirectory(), planElement.getName() + ".beh#" + planElement.getId()).toString());
        } else if(planElement instanceof PlanType) {
            jsonGenerator.writeString(Paths.get(planElement.getRelativeDirectory(), planElement.getName() + ".pty#" + planElement.getId()).toString());
        }
        //TODO Taskrepository is not an instance of abstract plan anymore
//        else if(planElement instanceof TaskRepository) {
//            jsonGenerator.writeString(Paths.get(planElement.getRelativeDirectory(), planElement.getName() + ".tsk#" + planElement.getId()).toString());
//        }
    }
}
