package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.RuntimeCondition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class RemoveRuntimeConditionFromPlan extends AbstractCommand {

    protected RuntimeCondition previousRuntimeCondition;
    protected Plan plan;

    public RemoveRuntimeConditionFromPlan(ModelManager modelManager, Plan plan, long conditionId) {
        super(modelManager);
        this.plan = plan;
        this.previousRuntimeCondition = (RuntimeCondition) modelManager.getPlanElement(conditionId);
    }

    @Override
    public void doCommand() {
        modelManager.removedPlanElement(Types.RUNTIMECONDITION, previousRuntimeCondition, plan, false);
        plan.setRuntimeCondition(null);
    }

    @Override
    public void undoCommand() {
        if (previousRuntimeCondition != null){
            modelManager.storePlanElement(Types.RUNTIMECONDITION, previousRuntimeCondition, plan, false);
        }
        plan.setRuntimeCondition(previousRuntimeCondition);
    }
}
