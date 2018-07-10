package de.uni_kassel.vs.cn.planDesigner.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.AnnotatedPlan;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.FileSystemUtil;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PlanTypePlansSerializer extends StdSerializer<ArrayList<AnnotatedPlan>> {

    public PlanTypePlansSerializer() {
        this(null);
    }

    public PlanTypePlansSerializer(Class<ArrayList<AnnotatedPlan>> t) {
        super(t);
    }

    @Override
    public void serialize(ArrayList<AnnotatedPlan> annotatedPlans, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String result = "";
        for (AnnotatedPlan annotatedPlan : annotatedPlans) {
            result += Paths.get(annotatedPlan.getPlan().getRelativeDirectory(), annotatedPlan.getPlan().getName() + "." + FileSystemUtil.PLAN_ENDING + "#" +
                    annotatedPlan.getPlan().getId()).toString() + "#" + annotatedPlan.isActivated() + ", ";
        }
        jsonGenerator.writeString(result);
    }
}
