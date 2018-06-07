package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Condition;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PreCondition;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.RuntimeCondition;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

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

    @Override
    public String getCommandString() {
        return "Add new condition to " + plan.getName();
    }
}
