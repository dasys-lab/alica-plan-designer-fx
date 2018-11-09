package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Synchronization;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PmlUiExtension;

public class DeleteSynchronisationFromPlan extends AbstractCommand {

    protected PlanModelVisualisationObject parentOfElement;
    protected PmlUiExtension pmlUiExtension;
    protected Synchronization synchronization;

    public DeleteSynchronisationFromPlan(ModelManager modelManager, Synchronization synchronization, PlanModelVisualisationObject parentOfElement) {
        super(modelManager);
        this.synchronization = synchronization;
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlan().getSynchronizations().remove(synchronization);
        pmlUiExtension = parentOfElement.getPmlUiExtensionMap().getExtension().get(synchronization);
        parentOfElement.getPmlUiExtensionMap().getExtension().remove(pmlUiExtension);
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlan().getSynchronizations().add(synchronization);
        parentOfElement.getPmlUiExtensionMap().getExtension().put(synchronization, pmlUiExtension);
    }
}
