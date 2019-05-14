package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alica.PostCondition;
import de.uni_kassel.vs.cn.planDesigner.alica.TerminalState;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;

public class AddPostConditionToTerminalState extends AbstractCommand<PostCondition> {

    private final TerminalState parentOfElement;
    private PostCondition previousPostcondition;

    public AddPostConditionToTerminalState(PostCondition element, TerminalState parentOfElement) {
        super(element, parentOfElement.getInPlan());
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        previousPostcondition = parentOfElement.getPostCondition();
        parentOfElement.setPostCondition(getElementToEdit());
    }

    @Override
    public void undoCommand() {
        parentOfElement.setPostCondition(previousPostcondition);
    }

    @Override
    public String getCommandString() {
        return "Add Postcondition " + getElementToEdit().getName() + " in TerminalState " + parentOfElement.getName();
    }
}
