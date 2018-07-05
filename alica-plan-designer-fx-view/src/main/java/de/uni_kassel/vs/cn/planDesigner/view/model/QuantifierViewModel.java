package de.uni_kassel.vs.cn.planDesigner.view.model;

import java.util.ArrayList;

public class QuantifierViewModel extends PlanElementViewModel{

    // TODO: make the sorts a property, or arrayList of properties?
    protected PlanElementViewModel scope;
    protected ArrayList<String> sorts;

    public QuantifierViewModel(long id, String name, String type) {
        super(id, name, type);
    }
}
