package de.unikassel.vs.alica.planDesigner.command.create;

import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.*;

public class CreatePlan extends AbstractCommand {

    protected Plan plan;

    public CreatePlan(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        if (mmq.getElementType().equals(Types.PLAN)) {
            this.plan = new Plan();
            this.plan.setName(mmq.getName());
            this.plan.setRelativeDirectory(modelManager.makeRelativeDirectory(mmq.getAbsoluteDirectory(), plan.getName() + "." + Extensions.PLAN));
        }
        else
        {
            System.err.println("CreatePlan: Type does not match command!");
        }
    }

    @Override
    public void doCommand() {
        modelManager.storePlanElement(Types.PLAN, plan, null, true);
    }

    @Override
    public void undoCommand() {
        modelManager.removedPlanElement(Types.PLAN, plan, null, true);
    }
}
