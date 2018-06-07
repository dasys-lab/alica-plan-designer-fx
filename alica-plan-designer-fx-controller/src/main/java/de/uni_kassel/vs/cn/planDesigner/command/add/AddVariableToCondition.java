package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Variable;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;

import static de.uni_kassel.vs.cn.generator.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 26.02.17.
 */
public class AddVariableToCondition extends AbstractCommand<Variable> {

    private final Condition parentOfElement;

    public AddVariableToCondition(Condition parentOfElement, Plan affectedPlan) {
        super(getAlicaFactory().createVariable(), affectedPlan);
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        parentOfElement.getVariables().add(getElementToEdit());
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
