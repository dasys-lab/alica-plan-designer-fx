package de.uni_kassel.vs.cn.planDesigner.view.model;

import java.util.ArrayList;

public class BehaviourViewModel extends PlanElementViewModel {

    protected String relativeDirectory;
    int frequency;
    long delay;
    ArrayList<VariableViewModel> variables;

    public BehaviourViewModel(long id, String name, String type) {
        super(id, name, type);
        variables = new ArrayList<>();
    }

    public void setRelativeDirectory(String relativeDirectory) {
        this.relativeDirectory = relativeDirectory;
    }

    public void addVariable(VariableViewModel variable) {
        variables.add(variable);
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getFrequency() {
        return frequency;
    }

    public long getDelay() {
        return delay;
    }

    public ArrayList<VariableViewModel> getVariables() {
        return variables;
    }
}
