package de.unikassel.vs.alica.planDesigner.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.unikassel.vs.alica.planDesigner.alicamodel.*;

import java.io.IOException;
import java.nio.file.Paths;

public class ExternalRefSerializer extends StdSerializer<PlanElement> {

    public ExternalRefSerializer() {
        this(null);
    }

    public ExternalRefSerializer(Class<PlanElement> t) {
        super(t);
    }

    @Override
    public void serialize(PlanElement planElement, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (planElement instanceof Plan) {
            jsonGenerator.writeString(Paths.get(((SerializablePlanElement) planElement).getRelativeDirectory(), planElement.getName() + ".pml#" + planElement.getId()).toString());
        } else if (planElement instanceof Behaviour) {
            jsonGenerator.writeString(Paths.get(((SerializablePlanElement)planElement).getRelativeDirectory(), planElement.getName() + ".beh#" + planElement.getId()).toString());
        } else if (planElement instanceof PlanType) {
            jsonGenerator.writeString(Paths.get(((SerializablePlanElement)planElement).getRelativeDirectory(), planElement.getName() + ".pty#" + planElement.getId()).toString());
        } else if (planElement instanceof TaskRepository) {
            jsonGenerator.writeString(Paths.get(((SerializablePlanElement)planElement).getRelativeDirectory(), planElement.getName() + ".tsk#" + planElement.getId()).toString());
        } else if (planElement instanceof Task) {
            TaskRepository taskRepository = ((Task) planElement).getTaskRepository();
            jsonGenerator.writeString(Paths.get(taskRepository.getRelativeDirectory(), taskRepository.getName() + ".tsk#" + planElement.getId()).toString());
        }
        else {
            throw new RuntimeException("ExternalRefSerializer: Unkown type to serialize... :D");
        }

    }
}
