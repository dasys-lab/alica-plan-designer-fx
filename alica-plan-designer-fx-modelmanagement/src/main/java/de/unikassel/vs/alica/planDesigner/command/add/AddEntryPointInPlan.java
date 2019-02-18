package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.EntryPoint;
import de.unikassel.vs.alica.planDesigner.alicamodel.Task;
import de.unikassel.vs.alica.planDesigner.command.AbstractUiPositionCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanUiExtensionPair;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;

import java.util.Map;


public class AddEntryPointInPlan extends AbstractUiPositionCommand {
    protected PlanUiExtensionPair parentOfElement;
    protected UiExtension newlyCreatedUiExtension;
    protected EntryPoint entryPoint;
    protected Map<String, Long> relatedObjects;

    public AddEntryPointInPlan(ModelManager manager, ModelModificationQuery mmq) {
        super(manager, mmq);
        this.relatedObjects = mmq.getRelatedObjects();
        this.parentOfElement =  manager.getPlanUIExtensionPair(mmq.getParentId());
    }

    @Override
    public void doCommand() {
        this.entryPoint = new EntryPoint();
        this.entryPoint.setPlan(this.parentOfElement.getPlan());
        this.entryPoint.setTask((Task) this.modelManager.getPlanElement(this.relatedObjects.get(Types.TASK)));
        this.newlyCreatedUiExtension = this.parentOfElement.getUiExtension(this.entryPoint);
        this.newlyCreatedUiExtension.setX(this.x);
        this.newlyCreatedUiExtension.setY(this.y);
        this.modelManager.createdPlanElement(Types.ENTRYPOINT, this.entryPoint, this.entryPoint.getPlan(), false);
    }

    @Override
    public void undoCommand() {
        this.parentOfElement.remove(entryPoint);
        this.modelManager.removedPlanElement(Types.ENTRYPOINT, this.entryPoint, this.parentOfElement.getPlan(), false);
    }
}
