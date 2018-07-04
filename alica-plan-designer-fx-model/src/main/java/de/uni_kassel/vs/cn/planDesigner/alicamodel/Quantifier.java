package de.uni_kassel.vs.cn.planDesigner.alicamodel;


import java.util.ArrayList;

public class Quantifier extends PlanElement {

    protected PlanElement scope;
    protected ArrayList<String> sorts;

    public ArrayList<String> getSorts() {
        return sorts;
    }

    public PlanElement getScope() {
        return scope;
    }

    public void setScope(PlanElement scope) {
        this.scope = scope;
    }
}
