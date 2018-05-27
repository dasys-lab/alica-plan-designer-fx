package de.uni_kassel.vs.cn.planDesigner.model;

import java.util.ArrayList;

public class Quantifier extends PlanElement {

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
