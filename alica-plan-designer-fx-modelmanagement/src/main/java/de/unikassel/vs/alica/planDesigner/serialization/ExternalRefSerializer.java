package de.unikassel.vs.alica.planDesigner.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Extensions;

import java.io.IOException;
import java.io.StringWriter;
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
            jsonGenerator.writeString(Paths.get(((SerializablePlanElement) planElement).getRelativeDirectory(), planElement.getName() + "." + Extensions.PLAN + "#" + planElement.getId()).toString());
        } else if (planElement instanceof Behaviour) {
            jsonGenerator.writeString(Paths.get(((SerializablePlanElement)planElement).getRelativeDirectory(), planElement.getName() + "." + Extensions.BEHAVIOUR + "#" + planElement.getId()).toString());
        } else if (planElement instanceof PlanType) {
            jsonGenerator.writeString(Paths.get(((SerializablePlanElement)planElement).getRelativeDirectory(), planElement.getName() + "." + Extensions.PLANTYPE + "#" + planElement.getId()).toString());
        } else if (planElement instanceof TaskRepository) {
            jsonGenerator.writeString(Paths.get(((SerializablePlanElement)planElement).getRelativeDirectory(), planElement.getName() + "." + Extensions.TASKREPOSITORY + "#" + planElement.getId()).toString());
        } else if (planElement instanceof Task) {
            TaskRepository taskRepository = ((Task) planElement).getTaskRepository();
            jsonGenerator.writeFieldName(Paths.get(taskRepository.getRelativeDirectory(), taskRepository.getName() + "." + Extensions.TASKREPOSITORY + "#" + planElement.getId()).toString());
        } else if (planElement instanceof RoleSet) {
            jsonGenerator.writeString(Paths.get(((SerializablePlanElement)planElement).getRelativeDirectory(), planElement.getName() + "." + Extensions.ROLESET + "#" + planElement.getId()).toString());
        } else if (planElement instanceof Variable) {
            // special case for external reference to variable from within a variable binding
            SerializablePlanElement parent = ((VariableBinding)jsonGenerator.getCurrentValue()).getSubPlan();
            if (parent instanceof PlanType) {
                jsonGenerator.writeString(Paths.get(parent.getRelativeDirectory(), parent.getName() + "." + Extensions.PLANTYPE + "#" + planElement.getId()).toString());
            } else if (parent instanceof Behaviour) {
                jsonGenerator.writeString(Paths.get(parent.getRelativeDirectory(), parent.getName() + "." + Extensions.BEHAVIOUR + "#" + planElement.getId()).toString());
            } else if (parent instanceof Plan) {
                jsonGenerator.writeString(Paths.get(parent.getRelativeDirectory(), parent.getName() + "." + Extensions.PLAN+ "#" + planElement.getId()).toString());
            }
        } else {
            throw new RuntimeException("ExternalRefSerializer: Unkown type to serialize... :P");
        }
    }
}
