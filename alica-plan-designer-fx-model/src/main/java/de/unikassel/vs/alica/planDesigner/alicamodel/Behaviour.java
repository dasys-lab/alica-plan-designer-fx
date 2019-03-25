package de.unikassel.vs.alica.planDesigner.alicamodel;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Behaviour extends AbstractPlan {
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
        if (preCondition != null) {
            InvalidationListener dirty = obs -> this.setDirty(true);
            preCondition.nameProperty().addListener(dirty);
            preCondition.conditionStringProperty().addListener(dirty);
            preCondition.enabledProperty().addListener(dirty);
            preCondition.pluginNameProperty().addListener(dirty);
            preCondition.commentProperty().addListener(dirty);
        }
    }

    public RuntimeCondition getRuntimeCondition() {
        return runtimeCondition.get();
    }

    public void setRuntimeCondition(RuntimeCondition runtimeCondition) {
        this.runtimeCondition.set(runtimeCondition);
        if (runtimeCondition != null) {
            InvalidationListener dirty = ons -> this.setDirty(true);
            runtimeCondition.nameProperty().addListener(dirty);
            runtimeCondition.conditionStringProperty().addListener(dirty);
            runtimeCondition.enabledProperty().addListener(dirty);
            runtimeCondition.pluginNameProperty().addListener(dirty);
            runtimeCondition.commentProperty().addListener(dirty);
        }
    }

    public PostCondition getPostCondition() {
        return postCondition.get();
    }

    public void setPostCondition(PostCondition postCondition) {
        this.postCondition.set(postCondition);
        if (postCondition != null) {
            InvalidationListener dirty = obs -> this.setDirty(true);
            postCondition.nameProperty().addListener(dirty);
            postCondition.conditionStringProperty().addListener(dirty);
            postCondition.enabledProperty().addListener(dirty);
            postCondition.pluginNameProperty().addListener(dirty);
            postCondition.commentProperty().addListener(dirty);
        }
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
