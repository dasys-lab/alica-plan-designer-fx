package de.unikassel.vs.alica.planDesigner.alicamodel;

public class TerminalState extends State {
    protected PostCondition postCondition;

    public PostCondition getPostCondition() {
        return postCondition;
    }

    public void setPostCondition(PostCondition postCondition) {
        this.postCondition = postCondition;
    }
}
