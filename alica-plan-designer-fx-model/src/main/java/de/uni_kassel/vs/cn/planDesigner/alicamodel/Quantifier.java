package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.serialization.CustomPlanElementSerializer;

import java.util.ArrayList;

public class Quantifier extends PlanElement {

    @JsonSerialize(using = CustomPlanElementSerializer.class)
    protected IInhabitable scope;
    protected ArrayList<String> sorts;

    public ArrayList<String> getSorts() {
        return sorts;
    }

    public IInhabitable getScope() {
        return scope;
    }

    public void setScope(IInhabitable scope) {
        this.scope = scope;
    }
}
