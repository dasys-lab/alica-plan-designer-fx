package de.uni_kassel.vs.cn.planDesigner.view.model;

public class VariableViewModel extends ViewModelElement {

    String variableType;

    public VariableViewModel(long id , String name, String variableType) {
        super (id, name, variableType);

    }

    public void setVariableType(String variableType) {
        this.variableType = variableType;
    }

    public String getVariableType() {
        return this.variableType;
    }
}
