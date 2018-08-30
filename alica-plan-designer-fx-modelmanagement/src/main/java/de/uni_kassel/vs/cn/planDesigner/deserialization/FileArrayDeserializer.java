package de.uni_kassel.vs.cn.planDesigner.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ParsedModelReferences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class FileArrayDeserializer extends StdDeserializer<ArrayList<AbstractPlan>> {

    public FileArrayDeserializer() {
        this(null);
    }

    public FileArrayDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ArrayList<AbstractPlan> deserialize(
            JsonParser jsonparser,
            DeserializationContext context)
            throws IOException, JsonProcessingException {
        TreeNode tree = jsonparser.getCodec().readTree(jsonparser);
        ArrayList<AbstractPlan> planElements = new ArrayList<>();
        String planElementString = ((ValueNode) tree).asText();
        String[] planElementStrings = planElementString.split(",");
        if(planElementStrings.length == 1 ) {
            return planElements;
        }
        for (String string : planElementStrings) {
            string = string.trim();
            if(string.isEmpty()) {
                continue;
            }
            int idIndex = string.indexOf('#');
            string = string.substring(idIndex + 1);
            AbstractPlan planElement = new AbstractPlan(Long.parseLong(string));
            planElements.add(planElement);
            ParsedModelReferences.getInstance().addIncompletePlanElementInState(planElement.getId());
        }
        return planElements;
    }
}
