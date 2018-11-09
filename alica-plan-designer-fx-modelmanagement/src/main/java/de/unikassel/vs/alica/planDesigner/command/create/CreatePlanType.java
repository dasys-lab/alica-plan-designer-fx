package de.unikassel.vs.alica.planDesigner.command.create;

import de.unikassel.vs.alica.planDesigner.alicamodel.PlanType;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.FileSystemUtil;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class CreatePlanType extends AbstractCommand {
    private PlanType planType;

    public CreatePlanType(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        if (mmq.getElementType().equals(Types.PLANTYPE)) {
            this.planType = new PlanType();
            this.planType.setName(mmq.getName());
            this.planType.setRelativeDirectory(modelManager.makeRelativeDirectory(mmq.getAbsoluteDirectory(), planType.getName()+ "." + FileSystemUtil.PLANTYPE_ENDING));
        }
        else
        {
            System.err.println("CreatePlanType: Type does not match command!");
        }
    }

    @Override
    public void doCommand() {
        modelManager.serializeToDisk(planType, FileSystemUtil.PLANTYPE_ENDING, true);
    }

    @Override
    public void undoCommand() {
        modelManager.removeFromDisk(planType, FileSystemUtil.PLANTYPE_ENDING, true);
    }
}
