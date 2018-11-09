package de.unikassel.vs.alica.planDesigner.view.model;

import javafx.beans.property.*;

import java.util.ArrayList;

public class BehaviourViewModel extends SerializableViewModel {

    protected StringProperty relativeDirectory = new SimpleStringProperty();
    protected IntegerProperty frequency = new SimpleIntegerProperty();
    protected LongProperty deferring = new SimpleLongProperty();

    protected ArrayList<VariableViewModel> variables;
    protected ConditionViewModel preCondition;
    protected ConditionViewModel runtimeCondition;
    protected ConditionViewModel postCondition;

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

    public ConditionViewModel getPreCondition() {
        return preCondition;
    }

    public void setPreCondition(ConditionViewModel conditionViewModel) {
        this.preCondition = conditionViewModel;
    }

    public ConditionViewModel getRuntimeCondition() {
        return runtimeCondition;
    }

    public void setRuntimeCondition(ConditionViewModel conditionViewModel) {
        this.runtimeCondition = conditionViewModel;
    }

    public ConditionViewModel getPostCondition() {
        return postCondition;
    }

    public void setPostCondition(ConditionViewModel conditionViewModel) {
        this.postCondition = conditionViewModel;
    }
}
