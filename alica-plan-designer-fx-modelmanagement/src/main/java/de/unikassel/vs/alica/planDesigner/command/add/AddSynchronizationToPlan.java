package de.unikassel.vs.alica.planDesigner.command.add;


import de.unikassel.vs.alica.planDesigner.alicamodel.Synchronisation;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanUiExtensionPair;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;

public class AddSynchronizationToPlan extends AbstractCommand {

    private PlanUiExtensionPair parentOfElement;
    private UiExtension newlyCreatedUiExtension;
    protected Synchronisation synchronisation;

    public AddSynchronizationToPlan(ModelManager manager, Synchronisation synchronisation, PlanUiExtensionPair parentOfElement, UiExtension extension) {
        super(manager);
        this.synchronisation = synchronisation;
        this.parentOfElement = parentOfElement;
        this.newlyCreatedUiExtension = extension;
    }

    @Override
    public void doCommand() {
        /*
        parentOfElement.getPlan().getSynchronisations().put(synchronisation);
        parentOfElement
                .getPmlUiExtensionMap()
                .getExtension()
                .put(synchronisation, newlyCreatedUiExtension);*/
        parentOfElement.put(synchronisation, newlyCreatedUiExtension);
        modelManager.createdPlanElement(Types.SYNCHRONISATION, synchronisation, parentOfElement.getPlan(), false);
    }

    @Override
    public void undoCommand() {
       /* parentOfElement.getPlan().getSynchronisations().remove(synchronisation);
        parentOfElement
                .getPmlUiExtensionMap()
                .getExtension()
                .remove(synchronisation);*/
        parentOfElement.remove(synchronisation);
        modelManager.createdPlanElement(Types.SYNCHRONISATION, synchronisation, parentOfElement.getPlan(), false);
    }
}
