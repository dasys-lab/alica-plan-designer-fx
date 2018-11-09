package de.unikassel.vs.alica.planDesigner.command.create;

import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.FileSystemUtil;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class CreatePlan extends AbstractCommand {

    protected Plan plan;

    public CreatePlan(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        if (mmq.getElementType().equals(Types.PLAN)) {
            this.plan = new Plan();
            this.plan.setName(mmq.getName());
            this.plan.setRelativeDirectory(modelManager.makeRelativeDirectory(mmq.getAbsoluteDirectory(), plan.getName() + "." + FileSystemUtil.PLAN_ENDING));
        }
        else
        {
            System.err.println("CreatePlan: Type does not match command!");
        }
    }

    @Override
    public void doCommand() {
        modelManager.serializeToDisk(plan, FileSystemUtil.PLAN_ENDING,true);
    }

    @Override
    public void undoCommand() {
        modelManager.removeFromDisk(plan, FileSystemUtil.PLAN_ENDING, true);
    }
}
