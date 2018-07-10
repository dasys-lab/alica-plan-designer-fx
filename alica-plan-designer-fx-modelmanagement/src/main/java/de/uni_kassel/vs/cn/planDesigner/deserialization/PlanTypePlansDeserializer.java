package de.uni_kassel.vs.cn.planDesigner.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ValueNode;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.AnnotatedPlan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ParsedModelReferences;

import java.io.IOException;
import java.util.ArrayList;

public class PlanTypePlansDeserializer extends StdDeserializer<ArrayList<AnnotatedPlan>> {

    public PlanTypePlansDeserializer() {
        this(null);
    }

    public PlanTypePlansDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ArrayList<AnnotatedPlan> deserialize(
            JsonParser jsonparser,
            DeserializationContext context)
            throws IOException, JsonProcessingException {
        TreeNode tree = jsonparser.getCodec().readTree(jsonparser);
        ArrayList<AnnotatedPlan> plans = new ArrayList<>();
        String plansString = ((ValueNode) tree).asText();
        String[] split = plansString.split(",");
        for(String planString : split) {
            if(planString.trim().isEmpty()) {
                continue;
            }
            String[] values = planString.split("#");
            Plan plan = new Plan(Long.parseLong(values[1]));
            AnnotatedPlan annotatedPlan = new AnnotatedPlan(plan);
            annotatedPlan.setActivated(Boolean.parseBoolean(values[2]));
            ParsedModelReferences.getInstance().addIncompletePlanInPlanTypes(annotatedPlan);
            plans.add(annotatedPlan);
        }


        return plans;
    }
}
