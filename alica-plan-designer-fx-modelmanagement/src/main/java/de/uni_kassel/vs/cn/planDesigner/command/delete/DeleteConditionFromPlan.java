package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PreCondition;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.RuntimeCondition;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

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
