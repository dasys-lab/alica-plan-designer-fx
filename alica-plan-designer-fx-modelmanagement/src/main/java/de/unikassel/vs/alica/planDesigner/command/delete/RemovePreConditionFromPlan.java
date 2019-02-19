package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.PreCondition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class RemovePreConditionFromPlan extends AbstractCommand {

    protected PreCondition previousPreCondition;
    protected Plan plan;

    public RemovePreConditionFromPlan(ModelManager modelManager, Plan plan, long conditionId) {
        super(modelManager);
        this.plan = plan;
        this.previousPreCondition = (PreCondition) modelManager.getPlanElement(conditionId);
    }

    @Override
    public void doCommand() {
        modelManager.removedPlanElement(Types.PRECONDITION, previousPreCondition, plan, false);
        plan.setPreCondition(null);
    }

    @Override
    public void undoCommand() {
        if(previousPreCondition != null){
            modelManager.createdPlanElement(Types.PRECONDITION, previousPreCondition, plan, false);
        }
        plan.setPreCondition(previousPreCondition);
    }
}
