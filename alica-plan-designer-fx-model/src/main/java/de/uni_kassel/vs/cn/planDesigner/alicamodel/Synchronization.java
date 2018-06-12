package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.uni_kassel.vs.cn.planDesigner.serialization.CustomPlanElementSerializer;

import java.util.ArrayList;

public class Synchronization extends PlanElement{
    protected int talkTimeout;
    protected int syncTimeout;
    protected boolean failOnSyncTimeout;
    @JsonSerialize(using = CustomPlanElementSerializer.class)
    protected ArrayList<Transition> synchedTransitions;

    public int getTalkTimeout() {
        return talkTimeout;
    }

    public void setTalkTimeout(int talkTimeout) {
        this.talkTimeout = talkTimeout;
    }

    public int getSyncTimeout() {
        return syncTimeout;
    }

    public void setSyncTimeout(int syncTimeout) {
        this.syncTimeout = syncTimeout;
    }

    public void setFailOnSyncTimeout(boolean failOnSyncTimeout) {
        this.failOnSyncTimeout = failOnSyncTimeout;
    }

    public boolean getFailOnSyncTimeout() {
        return failOnSyncTimeout;
    }

    public ArrayList<Transition> getSynchedTransitions() {
        return synchedTransitions;
    }
}
