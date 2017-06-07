package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.*;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 26.02.17.
 */
public class AddVariableToCondition extends AbstractCommand<Variable> {

    private final Condition parentOfElement;

    public AddVariableToCondition(Condition parentOfElement) {
        super(getAlicaFactory().createVariable(), parentOfElement.getAbstractPlan());
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        parentOfElement.getVars().add(getElementToEdit());
    }

    @Override
    public void undoCommand() {
        parentOfElement.getVars().remove(getElementToEdit());
    }

    @Override
    public String getCommandString() {
        return "Add new variable to" + parentOfElement.getName();
    }


}
