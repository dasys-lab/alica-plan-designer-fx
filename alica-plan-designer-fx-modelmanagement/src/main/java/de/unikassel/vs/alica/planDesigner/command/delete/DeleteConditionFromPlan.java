package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Condition;
import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.PreCondition;
import de.unikassel.vs.alica.planDesigner.alicamodel.RuntimeCondition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;

public class DeleteConditionFromPlan extends AbstractCommand {

    protected Plan plan;
    protected Condition condition;

    public DeleteConditionFromPlan(ModelManager modelManager, Plan plan, Condition condition) {
        super(modelManager);
        this.plan = plan;
    }

    @Override
    public void doCommand() {
        if (condition instanceof RuntimeCondition) {
            plan.setRuntimeCondition(null);
        } else if (condition instanceof PreCondition) {
            plan.setPreCondition(null);
        }
    }

    @Override
    public void undoCommand() {
        if (condition instanceof RuntimeCondition) {
            plan.setRuntimeCondition((RuntimeCondition) condition);
        } else if (condition instanceof PreCondition) {
            plan.setPreCondition((PreCondition) condition);
        }
    }
}
