package de.uni_kassel.vs.cn.planDesigner.command.create;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.FileSystemUtil;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelModificationQuery;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.Types;

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
