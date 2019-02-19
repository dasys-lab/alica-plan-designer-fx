package de.unikassel.vs.alica.planDesigner.alicamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Synchronisation extends PlanElement{

    @JsonIgnore
    protected final SimpleBooleanProperty dirty = new SimpleBooleanProperty();
    protected final SimpleIntegerProperty talkTimeout = new SimpleIntegerProperty();
    protected final SimpleIntegerProperty syncTimeout = new SimpleIntegerProperty();
    protected final SimpleBooleanProperty failOnSyncTimeout = new SimpleBooleanProperty();
    protected final ArrayList<Transition> syncedTransitions = new ArrayList<>();

    public Synchronisation(){}
    public Synchronisation(long id) {
        this.id = id;
    }

    public boolean getDirty() {
        return dirty.get();
    }
    public void setDirty(boolean dirty) {
        this.dirty.set(dirty);
    }
    public SimpleBooleanProperty dirtyProperty() {
        return dirty;
    }

    public int getTalkTimeout() {
        return talkTimeout.get();
    }
    public void setTalkTimeout(int talkTimeout) {
        this.talkTimeout.set(talkTimeout);
    }
    public SimpleIntegerProperty talkTimeoutProperty() {
        return talkTimeout;
    }

    public int getSyncTimeout() {
        return syncTimeout.get();
    }
    public void setSyncTimeout(int syncTimeout) {
        this.syncTimeout.set(syncTimeout);
    }
    public SimpleIntegerProperty syncTimeoutProperty() {
        return syncTimeout;
    }

    public void setFailOnSyncTimeout(boolean failOnSyncTimeout) {
        this.failOnSyncTimeout.set(failOnSyncTimeout);
    }
    public boolean getFailOnSyncTimeout() {
        return failOnSyncTimeout.get();
    }
    public SimpleBooleanProperty failOnSyncTimeoutProperty() {
        return failOnSyncTimeout;
    }

    public void addSyncedTransition(Transition transition) {
        syncedTransitions.add(transition);
        this.setDirty(true);
    }
    public void removeSyncedTransition(Transition transition) {
        syncedTransitions.remove(transition);
        this.setDirty(true);
    }
    public List<Transition> getSyncedTransitions() {
        return Collections.unmodifiableList(syncedTransitions);
    }
}
