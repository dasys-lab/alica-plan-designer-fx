package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Variable;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;

public class AddVariableToCondition extends AbstractCommand {

    private final Condition parentOfElement;

    public AddVariableToCondition(Condition parentOfElement, Plan affectedPlan) {
        super(new Variable(), affectedPlan);
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        parentOfElement.getVariables().add((Variable)getElementToEdit());
    }

    @Override
    public void undoCommand() {
        parentOfElement.getVariables().remove(getElementToEdit());
    }

    @Override
    public String getCommandString() {
        return "Add new variable to" + parentOfElement.getName();
    }


}
