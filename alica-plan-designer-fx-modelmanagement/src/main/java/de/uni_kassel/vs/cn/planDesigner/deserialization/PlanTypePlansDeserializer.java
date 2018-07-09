package de.uni_kassel.vs.cn.planDesigner.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ValueNode;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ParsedModelReferences;

import java.io.IOException;
import java.util.ArrayList;

public class PlanTypePlansDeserializer extends StdDeserializer<ArrayList<Plan>> {

    public PlanTypePlansDeserializer() {
        this(null);
    }

    public PlanTypePlansDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ArrayList<Plan> deserialize(
            JsonParser jsonparser,
            DeserializationContext context)
            throws IOException, JsonProcessingException {
        TreeNode tree = jsonparser.getCodec().readTree(jsonparser);
        ArrayList<Plan> plans = new ArrayList<>();
        String plansString = ((ValueNode) tree).asText();
        String[] split = plansString.split(",");
        for(String planString : split) {
            if(planString.trim().isEmpty()) {
                continue;
            }
            int idIndex = planString.indexOf('#');
            planString = planString.substring(idIndex + 1);
            Plan plan = new Plan(Long.parseLong(planString));
            ParsedModelReferences.getInstance().addIncompletePlanInPlanTypes(plan);
            plans.add(plan);
        }


        return plans;
    }
}
