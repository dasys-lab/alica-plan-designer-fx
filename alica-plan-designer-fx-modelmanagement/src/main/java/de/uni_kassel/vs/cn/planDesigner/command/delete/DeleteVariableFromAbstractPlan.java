package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Variable;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;

public class DeleteVariableFromAbstractPlan extends AbstractCommand {

    private final PlanElement parentOfElement;

    public DeleteVariableFromAbstractPlan(Variable toDelete, PlanElement parentOfElement) {
        super(toDelete, parentOfElement);
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        if (parentOfElement instanceof Plan) {
            ((Plan)parentOfElement).getVariables().remove(getElementToEdit());
        } else {
            ((Behaviour)parentOfElement).getVariables().remove(getElementToEdit());
        }
    }

    @Override
    public void undoCommand() {
        if (parentOfElement instanceof Plan) {
            ((Plan)parentOfElement).getVariables().add((Variable) getElementToEdit());
        } else {
            ((Behaviour)parentOfElement).getVariables().add((Variable) getElementToEdit());
        }
    }

    @Override
    public String getCommandString() {
        return "Remove Variable " + getElementToEdit().getName() + " from " + parentOfElement.getName();
    }
}
