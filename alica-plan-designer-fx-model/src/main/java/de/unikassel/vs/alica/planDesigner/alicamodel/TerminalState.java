package de.unikassel.vs.alica.planDesigner.alicamodel;

import javafx.beans.InvalidationListener;

public class TerminalState extends State {
    protected PostCondition postCondition;

    private boolean success;

    public TerminalState(){}

    public TerminalState(boolean success, PostCondition postCondition) {
        this.success = success;
        this.postCondition = postCondition;
    }

    public PostCondition getPostCondition() {
        return postCondition;
    }

    public void setPostCondition(PostCondition postCondition) {
        this.postCondition = postCondition;
        if (postCondition != null) {
            InvalidationListener dirty = obs -> this.parentPlan.setDirty(true);
            postCondition.nameProperty().addListener(dirty);
            postCondition.conditionStringProperty().addListener(dirty);
            postCondition.enabledProperty().addListener(dirty);
            postCondition.pluginNameProperty().addListener(dirty);
            postCondition.commentProperty().addListener(dirty);
        }
    }

    public boolean isSuccess(){
        return success;
    }
}
