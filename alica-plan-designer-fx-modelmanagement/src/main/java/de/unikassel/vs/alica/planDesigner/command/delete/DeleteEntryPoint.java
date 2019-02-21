package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.EntryPoint;
import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import de.unikassel.vs.alica.planDesigner.command.UiPositionCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiElement;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;

public class DeleteEntryPoint extends UiPositionCommand {

    protected State associatedState;
    protected final UiExtension parentOfElement;
    protected UiElement uiElement;
    protected EntryPoint entryPoint;

    public DeleteEntryPoint(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.entryPoint = (EntryPoint) modelManager.getPlanElement(mmq.getElementId());
        this.parentOfElement = modelManager.getPlanUIExtensionPair(mmq.getParentId());
        this.uiElement = parentOfElement.getUiElement(entryPoint);
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
        uiElement = parentOfElement.getUiElement(entryPoint);
        uiElement.setX(this.x);
        uiElement.setY(this.y);
        if (associatedState != null) {
            associatedState.setEntryPoint(entryPoint);
        }
    }
}
