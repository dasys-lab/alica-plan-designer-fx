package de.unikassel.vs.alica.planDesigner.command.create;

import de.unikassel.vs.alica.planDesigner.alicamodel.PlanType;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.*;

public class CreatePlanType extends AbstractCommand {
    private PlanType planType;

    public CreatePlanType(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        if (mmq.getElementType().equals(Types.PLANTYPE)) {
            this.planType = new PlanType();
            this.planType.setName(mmq.getName());
            this.planType.setRelativeDirectory(modelManager.makeRelativeDirectory(mmq.getAbsoluteDirectory(), planType.getName()+ "." + Extensions.PLANTYPE));
        }
        else
        {
            System.err.println("CreatePlanType: Type does not match command!");
        }
    }

    @Override
    public void doCommand() {
        modelManager.storePlanElement(Types.PLANTYPE, planType, null, true);
    }

    @Override
    public void undoCommand() {
        modelManager.removedPlanElement(Types.PLANTYPE, planType, null, true);
    }
}
