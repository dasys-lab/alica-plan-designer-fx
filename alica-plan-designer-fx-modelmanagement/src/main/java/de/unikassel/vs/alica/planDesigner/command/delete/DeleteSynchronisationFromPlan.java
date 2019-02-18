package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Synchronisation;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanUiExtensionPair;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;

public class DeleteSynchronisationFromPlan extends AbstractCommand {

    protected PlanUiExtensionPair parentOfElement;
    protected UiExtension uiExtension;
    protected Synchronisation synchronisation;

    public DeleteSynchronisationFromPlan(ModelManager modelManager, Synchronisation synchronisation, PlanUiExtensionPair parentOfElement) {
        super(modelManager);
        this.synchronisation = synchronisation;
        this.parentOfElement = parentOfElement;
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
        parentOfElement.put(synchronisation, uiExtension);
    }
}
