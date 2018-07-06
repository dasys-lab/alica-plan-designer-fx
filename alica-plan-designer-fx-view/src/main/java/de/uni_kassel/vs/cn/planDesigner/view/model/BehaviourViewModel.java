package de.uni_kassel.vs.cn.planDesigner.view.model;

import javafx.beans.property.*;

import java.util.ArrayList;

public class BehaviourViewModel extends PlanElementViewModel {

    protected StringProperty relativeDirectory = new SimpleStringProperty();
    protected IntegerProperty frequency = new SimpleIntegerProperty();
    protected LongProperty deferring = new SimpleLongProperty();

    protected ArrayList<VariableViewModel> variables;
    protected ArrayList<ConditionViewModel> preConditions;
    protected ArrayList<ConditionViewModel> runtimeConditions;
    protected ArrayList<ConditionViewModel> postConditions;

    public BehaviourViewModel(long id, String name, String type) {
        super(id, name, type);
        variables = new ArrayList<>();
    }

    public void setRelativeDirectory(String relativeDirectory) {
        this.relativeDirectory.set(relativeDirectory);
    }

    public String getRelativeDirectory() {
        return this.relativeDirectory.get();
    }

    public StringProperty relativeDirectoryProperty() {
        return relativeDirectory;
    }


    public void setDeferring(long deferring) {
        this.deferring.set(deferring);
    }

    public long getDeferring() {
        return deferring.get();
    }

    public LongProperty deferringProperty() {
        return deferring;
    }


    public void setFrequency(int frequency) {
        this.frequency.set(frequency);
    }

    public int getFrequency() {
        return frequency.get();
    }

    public IntegerProperty frequencyProperty() {
        return frequency;
    }


    public ArrayList<VariableViewModel> getVariables() {
        return variables;
    }

    public ArrayList<ConditionViewModel> getPreConditions() {
        return preConditions;
    }

    public ArrayList<ConditionViewModel> getRuntimeConditions() {
        return runtimeConditions;
    }

    public ArrayList<ConditionViewModel> getPostConditions() {
        return postConditions;
    }
}
