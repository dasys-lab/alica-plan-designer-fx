package de.uni_kassel.vs.cn.planDesigner.view.model;

import java.util.ArrayList;

public class BehaviourViewModel extends ViewModelElement {

    protected String relativeDirectory;
    int frequency;
    long delay;
    String comment;
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

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public int getFrequency() {
        return frequency;
    }

    public long getDelay() {
        return delay;
    }
}
