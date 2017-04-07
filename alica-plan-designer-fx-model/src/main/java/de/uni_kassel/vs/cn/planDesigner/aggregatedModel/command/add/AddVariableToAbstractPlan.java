package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.*;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 26.02.17.
 */
public class AddVariableToAbstractPlan extends AbstractCommand<Variable> {

    private final PlanElement parentOfElement;

    public AddVariableToAbstractPlan(PlanElement parentOfElement) {
        super(getAlicaFactory().createVariable());
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        if (parentOfElement instanceof Plan) {
            ((Plan)parentOfElement).getVars().add(getElementToEdit());
        } else {
            ((Behaviour)parentOfElement).getVars().add(getElementToEdit());
        }
    }

    @Override
    public void undoCommand() {
        if (parentOfElement instanceof Plan) {
            ((Plan)parentOfElement).getVars().remove(getElementToEdit());
        } else {
            ((Behaviour)parentOfElement).getVars().remove(getElementToEdit());
        }
    }

    @Override
    public String getCommandString() {
        return "Add new variable to" + parentOfElement.getName();
    }


}
