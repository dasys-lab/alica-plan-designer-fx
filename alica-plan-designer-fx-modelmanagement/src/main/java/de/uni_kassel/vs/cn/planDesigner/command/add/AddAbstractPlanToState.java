package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.State;

public class AddAbstractPlanToState extends AbstractCommand {
    private State parentOfElement;

    public AddAbstractPlanToState(AbstractPlan element, State parentOfElement) {
        super(element, parentOfElement.getParentPlan());
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        parentOfElement.addAbstractPlan((AbstractPlan) getElementToEdit());
    }

    @Override
    public void undoCommand() {
        parentOfElement.removeAbstractPlan((AbstractPlan) getElementToEdit());
    }

    @Override
    public String getCommandString() {
        return "Add Element " + getElementToEdit().getName() + " in State " + parentOfElement.getName();
    }
}
