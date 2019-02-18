package de.unikassel.vs.alica.planDesigner.alicamodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Behaviour extends AbstractPlan implements HasVariables {
    protected final SimpleIntegerProperty frequency = new SimpleIntegerProperty();
    protected final SimpleLongProperty deferring = new SimpleLongProperty();

    protected ObjectProperty<PreCondition> preCondition = new SimpleObjectProperty<>();
    protected ObjectProperty<RuntimeCondition> runtimeCondition = new SimpleObjectProperty<>();
    protected ObjectProperty<PostCondition> postCondition = new SimpleObjectProperty<>();
    protected final ArrayList<Variable> variables= new ArrayList<>();

    public PreCondition getPreCondition() {
        return preCondition.get();
    }

    public void setPreCondition(PreCondition preCondition) {
        this.preCondition.set(preCondition);
    }

    public RuntimeCondition getRuntimeCondition() {
        return runtimeCondition.get();
    }

    public void setRuntimeCondition(RuntimeCondition runtimeCondition) {
        this.runtimeCondition.set(runtimeCondition);
    }

    public PostCondition getPostCondition() {
        return postCondition.get();
    }

    public void setPostCondition(PostCondition postCondition) {
        this.postCondition.set(postCondition);
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
        // TODO: make listener in put method a local variable that is removed from the list of listeners here...
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


    @Override
    public void registerDirtyFlag() {
        super.registerDirtyFlag();
        preCondition    .addListener((observable, oldValue, newValue) -> this.setDirty(true));
        runtimeCondition.addListener((observable, oldValue, newValue) -> this.setDirty(true));
        postCondition   .addListener((observable, oldValue, newValue) -> this.setDirty(true));
    }
}
