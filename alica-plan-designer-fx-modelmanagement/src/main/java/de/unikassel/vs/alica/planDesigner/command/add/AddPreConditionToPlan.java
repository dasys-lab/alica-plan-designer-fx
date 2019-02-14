package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.alicamodel.PreCondition;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class AddPreConditionToPlan extends AbstractCommand {

    protected Plan plan;
    protected PreCondition newPreCondition;
    protected PreCondition previousPreCondition;

    public AddPreConditionToPlan(ModelManager modelManager, PreCondition preCondition, Plan plan) {
        super(modelManager);
        this.newPreCondition = preCondition;
        this.plan = plan;
        this.previousPreCondition = plan.getPreCondition();
    }

    @Override
    public void doCommand() {
        modelManager.createdPlanElement(Types.PRECONDITION, newPreCondition, plan, false);
        plan.setPreCondition(newPreCondition);
    }

    @Override
    public void undoCommand() {
        if(previousPreCondition == null){
            modelManager.removedPlanElement(Types.PRECONDITION, newPreCondition, plan, false);
        }else{
            modelManager.createdPlanElement(Types.PRECONDITION, previousPreCondition, plan, false);
        }
        plan.setPreCondition(previousPreCondition);
    }
}
