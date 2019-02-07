package de.unikassel.vs.alica.planDesigner.alicamodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Behaviour extends AbstractPlan implements HasVariables {
    protected final SimpleIntegerProperty frequency = new SimpleIntegerProperty();
    protected final SimpleLongProperty deferring = new SimpleLongProperty();

    protected PreCondition preCondition;
    protected RuntimeCondition runtimeCondition;
    protected PostCondition postCondition;
    protected final ArrayList<Variable> variables= new ArrayList<>();

    public PreCondition getPreCondition() {
        return preCondition;
    }

    public void setPreCondition(PreCondition preCondition) {
        this.preCondition = preCondition;
    }

    public RuntimeCondition getRuntimeCondition() {
        return runtimeCondition;
    }

    public void setRuntimeCondition(RuntimeCondition runtimeCondition) {
        this.runtimeCondition = runtimeCondition;
    }

    public PostCondition getPostCondition() {
        return postCondition;
    }

    public void setPostCondition(PostCondition postCondition) {
        this.postCondition = postCondition;
    }

    public void addVariable(Variable variable) {
        variables.add(variable);
        variable.nameProperty().addListener((observable, oldValue, newValue) -> {
            this.setDirty(true);
        });
        variable.commentProperty().addListener((observable, oldValue, newValue) -> {
            this.setDirty(true);
        });
        variable.variableTypeProperty().addListener((observable, oldValue, newValue) -> {
            this.setDirty(true);
        });
        this.setDirty(true);
    }
    public void removeVariable(Variable variable) {
        // TODO: make listener in add method a local variable that is removed from the list of listeners here...
        variables.remove(variable);
        this.setDirty(true);
    }
    public List<Variable> getVariables() {
        return Collections.unmodifiableList(variables);
    }

    public int getFrequency() {
        return frequency.get();
    }

    public void setFrequency(int frequency) {
        this.frequency.set(frequency);
    }

    public SimpleIntegerProperty frequencyProperty() {
        return frequency;
    }

    public long getDeferring() {
        return deferring.get();
    }

    public void setDeferring(long deferring) {
        this.deferring.set(deferring);
    }

    public SimpleLongProperty deferringProperty() {
        return this.deferring;
    }


}
