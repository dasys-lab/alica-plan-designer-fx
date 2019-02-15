package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Synchronisation;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PmlUiExtension;

public class DeleteSynchronisationFromPlan extends AbstractCommand {

    protected PlanModelVisualisationObject parentOfElement;
    protected PmlUiExtension pmlUiExtension;
    protected Synchronisation synchronisation;

    public DeleteSynchronisationFromPlan(ModelManager modelManager, Synchronisation synchronisation, PlanModelVisualisationObject parentOfElement) {
        super(modelManager);
        this.synchronisation = synchronisation;
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlan().getSynchronisations().remove(synchronisation);
        pmlUiExtension = parentOfElement.getPmlUiExtensionMap().getExtension().get(synchronisation);
        parentOfElement.getPmlUiExtensionMap().getExtension().remove(pmlUiExtension);
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlan().getSynchronisations().add(synchronisation);
        parentOfElement.getPmlUiExtensionMap().getExtension().put(synchronisation, pmlUiExtension);
    }
}
