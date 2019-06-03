package de.unikassel.vs.alica.planDesigner.alicamodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.*;

public class Behaviour extends AbstractPlan {
    protected final SimpleIntegerProperty frequency = new SimpleIntegerProperty(this, "frequency", 0);
    protected final SimpleLongProperty deferring = new SimpleLongProperty(this, "deferring", 0);

    protected SimpleObjectProperty<PreCondition> preCondition = new SimpleObjectProperty<>();
    protected SimpleObjectProperty<RuntimeCondition> runtimeCondition = new SimpleObjectProperty<>();
    protected SimpleObjectProperty<PostCondition> postCondition = new SimpleObjectProperty<>();

    private final Map<String, String> parameters = new HashMap<>();

    public PreCondition getPreCondition() {
        return preCondition.get();
    }
    public void setPreCondition(PreCondition preCondition) {
        this.preCondition.set(preCondition);
        if(preCondition != null) {
            preCondition.registerDirtyFlag(this.changeListenerForDirtyFlag);
        }
    }
    public SimpleObjectProperty<PreCondition> preConditionProperty() {
        return preCondition;
    }

    public RuntimeCondition getRuntimeCondition() {
        return runtimeCondition.get();
    }
    public void setRuntimeCondition(RuntimeCondition runtimeCondition) {
        this.runtimeCondition.set(runtimeCondition);
        if(runtimeCondition != null) {
            runtimeCondition.registerDirtyFlag(this.changeListenerForDirtyFlag);
        }
    }
    public SimpleObjectProperty<RuntimeCondition> runtimeConditionProperty() {
        return runtimeCondition;
    }

    public PostCondition getPostCondition() {
        return postCondition.get();
    }
    public void setPostCondition(PostCondition postCondition) {
        this.postCondition.set(postCondition);
        if(postCondition != null) {
            postCondition.registerDirtyFlag(this.changeListenerForDirtyFlag);
        }
    }
    public SimpleObjectProperty<PostCondition> postConditionProperty() {
        return postCondition;
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

    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    public String putParameter(String key, String value) {
        setDirty(true);
        return parameters.put(key, value);
    }

    public String removeParameter(String key) {
        setDirty(true);
        return parameters.remove(key);
    }

    public void replaceParameter(Map.Entry<String, String> newEntry, Map.Entry<String, String> oldEntry) {
        setDirty(true);

        if (oldEntry == null || (newEntry != null) && oldEntry.getKey() == newEntry.getKey()) {
            this.parameters.put(newEntry.getKey(), newEntry.getValue());
            return;
        }

        if (newEntry == null) {
            this.parameters.remove(oldEntry.getKey());
            return;
        }

        this.parameters.remove(oldEntry.getKey());
        this.parameters.put(newEntry.getKey(), newEntry.getValue());
    }

    @Override
    public void registerDirtyFlag() {
        super.registerDirtyFlag();
        this.frequency.addListener(this.changeListenerForDirtyFlag);
        this.deferring.addListener(this.changeListenerForDirtyFlag);
        this.preCondition.addListener(this.changeListenerForDirtyFlag);
        if (this.preCondition.get() != null) {
            this.preCondition.get().registerDirtyFlag(this.changeListenerForDirtyFlag);
        }
        this.runtimeCondition.addListener(this.changeListenerForDirtyFlag);
        if (this.runtimeCondition.get() != null) {
            this.runtimeCondition.get().registerDirtyFlag(this.changeListenerForDirtyFlag);
        }
        this.postCondition.addListener(this.changeListenerForDirtyFlag);
        if (this.postCondition.get() != null) {
            this.postCondition.get().registerDirtyFlag(this.changeListenerForDirtyFlag);
        }
    }

    @Override
    public void removeVariable(Variable variable) {
        super.removeVariable(variable);

        // Remove the Variable from all child-elements
        if(getPreCondition() != null) {
            getPreCondition().removeVariable(variable);
        }
        if(getRuntimeCondition() != null) {
            getRuntimeCondition().removeVariable(variable);
        }
        if(getPostCondition() != null) {
            getPostCondition().removeVariable(variable);
        }
    }
}
