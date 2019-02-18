package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Synchronisation;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.command.AbstractUiPositionCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanUiExtensionPair;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;

public class DeleteSynchronisationFromPlan extends AbstractUiPositionCommand {

    protected PlanUiExtensionPair parentOfElement;
    protected UiExtension uiExtension;
    protected Synchronisation synchronisation;

    public DeleteSynchronisationFromPlan(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.synchronisation = (Synchronisation) this.modelManager.getPlanElement(mmq.getElementId());
        this.parentOfElement = this.modelManager.getPlanUIExtensionPair(mmq.getParentId());
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlan().getSynchronisations().remove(synchronisation);
        uiExtension = parentOfElement.getUiExtension(synchronisation);
        parentOfElement.remove(uiExtension);
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlan().getSynchronisations().add(synchronisation);
        this.uiExtension = parentOfElement.getUiExtension(synchronisation);
        this.uiExtension.setX(this.x);
        this.uiExtension.setY(this.y);
    }
}
