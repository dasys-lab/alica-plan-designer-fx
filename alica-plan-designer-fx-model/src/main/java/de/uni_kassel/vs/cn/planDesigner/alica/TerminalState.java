package de.uni_kassel.vs.cn.planDesigner.alica;

public class TerminalState extends State {
    protected PostCondition postCondition;

    public PostCondition getPostCondition() {
        return postCondition;
    }

    public void setPostCondition(PostCondition postCondition) {
        this.postCondition = postCondition;
    }
}
