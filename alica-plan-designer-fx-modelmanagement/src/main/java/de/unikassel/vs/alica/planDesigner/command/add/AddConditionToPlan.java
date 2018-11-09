package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.Condition;
import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.PreCondition;
import de.unikassel.vs.alica.planDesigner.alicamodel.RuntimeCondition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;

public class AddConditionToPlan extends AbstractCommand {

    protected Condition condition;
    protected Plan plan;

    public AddConditionToPlan(ModelManager modelManager, Condition condition, Plan plan) {
        super(modelManager);
        this.condition = condition;
        this.plan = plan;
    }

    @Override
    public void doCommand() {
        if (condition instanceof PreCondition) {
            plan.setPreCondition((PreCondition) condition);
        } else if (condition instanceof RuntimeCondition) {
            plan.setRuntimeCondition((RuntimeCondition) condition);
        }
    }

    @Override
    public void undoCommand() {
        if (condition instanceof PreCondition) {
            plan.setPreCondition((PreCondition)condition);
        } else if (condition instanceof RuntimeCondition) {
            plan.setRuntimeCondition((RuntimeCondition) condition);
        }
    }
}
