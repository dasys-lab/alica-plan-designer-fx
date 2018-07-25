package de.uni_kassel.vs.cn.planDesigner.command.create;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanType;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.FileSystemUtil;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelModificationQuery;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.Types;

public class CreatePlanType extends AbstractCommand {
    private PlanType planType;

    public CreatePlanType(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        if (mmq.getElementType().equals(Types.PLANTYPE)) {
            this.planType = new PlanType();
            this.planType.setName(mmq.getName());
            this.planType.setRelativeDirectory(modelManager.makeRelativePlansDirectory(mmq.getAbsoluteDirectory(), planType.getName()+ "." + FileSystemUtil.PLANTYPE_ENDING));
        }
        else
        {
            System.err.println("CreatePlanType: Type does not match command!");
        }
    }

    @Override
    public void doCommand() {
        modelManager.serializeToDisk(planType, FileSystemUtil.PLANTYPE_ENDING);
    }

    @Override
    public void undoCommand() {
        modelManager.removeFromDisk(planType, FileSystemUtil.PLANTYPE_ENDING);
    }
}
