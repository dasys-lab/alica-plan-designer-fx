package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Variable;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;

import static de.uni_kassel.vs.cn.generator.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 26.02.17.
 */
public class AddVariableToAbstractPlan extends AbstractCommand<Variable> {

    private final PlanElement parentOfElement;

    public AddVariableToAbstractPlan(PlanElement parentOfElement) {
        super(getAlicaFactory().createVariable(), parentOfElement);
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
