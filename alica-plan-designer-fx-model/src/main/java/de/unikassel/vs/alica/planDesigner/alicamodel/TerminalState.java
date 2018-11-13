package de.unikassel.vs.alica.planDesigner.alicamodel;

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
    }

    public boolean isSuccess(){
        return success;
    }
}
