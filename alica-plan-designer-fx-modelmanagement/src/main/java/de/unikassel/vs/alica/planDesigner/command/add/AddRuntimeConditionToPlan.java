package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.RuntimeCondition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class AddRuntimeConditionToPlan extends AbstractCommand {

    protected Plan plan;
    protected RuntimeCondition newRuntimeCondition;
    protected RuntimeCondition previousRuntimeCondition;

    public AddRuntimeConditionToPlan(ModelManager modelManager, RuntimeCondition newRuntimeCondition, Plan plan) {
        super(modelManager);
        this.newRuntimeCondition = newRuntimeCondition;
        this.plan = plan;
        previousRuntimeCondition = plan.getRuntimeCondition();
    }

    @Override
    public void doCommand() {
        modelManager.createdPlanElement(Types.RUNTIMECONDITION, newRuntimeCondition, plan, false);
        plan.setRuntimeCondition(newRuntimeCondition);
    }

    @Override
    public void undoCommand() {
        if(previousRuntimeCondition == null){
            modelManager.removedPlanElement(Types.RUNTIMECONDITION, newRuntimeCondition, plan, false);
        }else {
            modelManager.createdPlanElement(Types.RUNTIMECONDITION, previousRuntimeCondition, plan, false);
        }
        plan.setRuntimeCondition(previousRuntimeCondition);
    }
}
