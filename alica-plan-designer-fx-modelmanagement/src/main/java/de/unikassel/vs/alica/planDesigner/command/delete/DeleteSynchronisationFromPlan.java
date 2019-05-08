package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Synchronisation;
import de.unikassel.vs.alica.planDesigner.command.UiPositionCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiElement;

public class DeleteSynchronisationFromPlan extends UiPositionCommand {

    protected UiExtension parentOfElement;
    protected UiElement uiElement;
    protected Synchronisation synchronisation;

    public DeleteSynchronisationFromPlan(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.synchronisation = (Synchronisation) this.modelManager.getPlanElement(mmq.getElementId());
        this.parentOfElement = this.modelManager.getPlanUIExtensionPair(mmq.getParentId());
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlan().getSynchronisations().remove(synchronisation);
        uiElement = parentOfElement.getUiElement(synchronisation.getId());
        parentOfElement.remove(synchronisation.getId());
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlan().getSynchronisations().add(synchronisation);
        parentOfElement.add(synchronisation.getId(), this.uiElement);
        this.uiElement.setX(this.x);
        this.uiElement.setY(this.y);
    }
}
