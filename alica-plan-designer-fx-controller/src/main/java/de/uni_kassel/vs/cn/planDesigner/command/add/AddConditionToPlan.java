package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PreCondition;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.RuntimeCondition;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;

/**
 * Created by marci on 02.12.16.
 */
public class AddConditionToPlan extends AbstractCommand<Condition> {

    private Plan parentOfElement;

    public AddConditionToPlan(Plan parentOfElement, Condition element) {
        super(element, parentOfElement);
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        if (getElementToEdit() instanceof PreCondition) {
            parentOfElement.setPreCondition((PreCondition) getElementToEdit());
        } else if (getElementToEdit() instanceof RuntimeCondition) {
            parentOfElement.setRuntimeCondition((RuntimeCondition) getElementToEdit());
        }
    }

    @Override
    public void undoCommand() {
        if (getElementToEdit() instanceof PreCondition) {
            parentOfElement.setPreCondition((PreCondition) getElementToEdit());
        } else if (getElementToEdit() instanceof RuntimeCondition) {
            parentOfElement.setRuntimeCondition((RuntimeCondition) getElementToEdit());
        }
    }

    @Override
    public String getCommandString() {
        return "Add new condition to " + parentOfElement.getName();
    }
}
