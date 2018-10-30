package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;

public class Synchronization extends PlanElement{

    protected final SimpleIntegerProperty talkTimeout = new SimpleIntegerProperty();
    protected final SimpleIntegerProperty syncTimeout = new SimpleIntegerProperty();
    protected final SimpleBooleanProperty failOnSyncTimeout = new SimpleBooleanProperty();

    public Synchronization(){}

    public Synchronization(long id) {
        this.id = id;
    }

    protected ArrayList<Transition> syncedTransitions;

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

    public ArrayList<Transition> getSyncedTransitions() {
        return syncedTransitions;
    }
}
