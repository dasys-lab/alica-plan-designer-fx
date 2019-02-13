package de.unikassel.vs.alica.planDesigner.view.model;

import de.unikassel.vs.alica.planDesigner.view.Types;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class BehaviourViewModel extends SerializableViewModel implements HasVariablesView {

    protected IntegerProperty frequency = new SimpleIntegerProperty();
    protected LongProperty deferring = new SimpleLongProperty();

    protected ObservableList<VariableViewModel> variables;
    protected final ObjectProperty<ConditionViewModel> preCondition;
    protected final ObjectProperty<ConditionViewModel> runtimeCondition;
    protected final ObjectProperty<ConditionViewModel> postCondition;

    public BehaviourViewModel(long id, String name, String type) {
        super(id, name, type);
        variables = FXCollections.observableArrayList(new ArrayList<>());
        preCondition        = new SimpleObjectProperty<>(this, Types.PRECONDITION, null);
        runtimeCondition    = new SimpleObjectProperty<>(this, Types.RUNTIMECONDITION, null);
        postCondition       = new SimpleObjectProperty<>(this, Types.POSTCONDITION, null);
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

    @Override
    public ObservableList<VariableViewModel> getVariables() {
        return variables;
    }

    public ObjectProperty<ConditionViewModel> preConditionProperty(){
        return preCondition;
    }

    public ConditionViewModel getPreCondition() {
        return preCondition.get();
    }

    public void setPreCondition(ConditionViewModel conditionViewModel) {
        this.preCondition.set(conditionViewModel);
    }

    public ObjectProperty<ConditionViewModel> runtimeConditionProperty(){
        return runtimeCondition;
    }

    public ConditionViewModel getRuntimeCondition() {
        return runtimeCondition.get();
    }

    public void setRuntimeCondition(ConditionViewModel conditionViewModel) {
        this.runtimeCondition.set(conditionViewModel);
    }

    public ObjectProperty<ConditionViewModel> posConditionProperty(){
        return postCondition;
    }

    public ConditionViewModel getPostCondition() {
        return postCondition.get();
    }

    public void setPostCondition(ConditionViewModel conditionViewModel) {
        this.postCondition.set(conditionViewModel);
    }
}
