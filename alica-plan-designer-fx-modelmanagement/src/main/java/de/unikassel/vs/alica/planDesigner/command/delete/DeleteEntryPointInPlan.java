package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.EntryPoint;
import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.command.AbstractUiPositionCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanUiExtensionPair;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;

public class DeleteEntryPointInPlan extends AbstractUiPositionCommand {

    protected State associatedState;
    protected final PlanUiExtensionPair parentOfElement;
    protected UiExtension uiExtension;
    protected EntryPoint entryPoint;

    public DeleteEntryPointInPlan(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.entryPoint = (EntryPoint) modelManager.getPlanElement(mmq.getElementId());
        this.parentOfElement = modelManager.getPlanUIExtensionPair(mmq.getParentId());
        this.uiExtension = parentOfElement.getUiExtension(entryPoint);
        this.associatedState = entryPoint.getState();
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlan().getEntryPoints().remove(entryPoint);
        parentOfElement.remove(entryPoint);
        if (associatedState != null) {
            associatedState.setEntryPoint(null);
        }
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlan().getEntryPoints().add(entryPoint);
        uiExtension = parentOfElement.getUiExtension(entryPoint);
        uiExtension.setX(this.x);
        uiExtension.setY(this.y);
        if (associatedState != null) {
            associatedState.setEntryPoint(entryPoint);
        }
    }
}
