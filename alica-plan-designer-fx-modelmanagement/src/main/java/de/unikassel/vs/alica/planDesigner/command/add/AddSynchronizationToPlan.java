package de.unikassel.vs.alica.planDesigner.command.add;


import de.unikassel.vs.alica.planDesigner.alicamodel.Synchronisation;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PmlUiExtension;

public class AddSynchronizationToPlan extends AbstractCommand {

    private PlanModelVisualisationObject parentOfElement;
    private PmlUiExtension newlyCreatedPmlUiExtension;
    protected Synchronisation synchronisation;

    public AddSynchronizationToPlan(ModelManager manager, Synchronisation synchronisation, PlanModelVisualisationObject parentOfElement, PmlUiExtension extension) {
        super(manager);
        this.synchronisation = synchronisation;
        this.parentOfElement = parentOfElement;
        this.newlyCreatedPmlUiExtension = extension;
    }

    @Override
    public void doCommand() {
        /*
        parentOfElement.getPlan().getSynchronisations().add(synchronisation);
        parentOfElement
                .getPmlUiExtensionMap()
                .getExtension()
                .put(synchronisation, newlyCreatedPmlUiExtension);*/
        parentOfElement.getPmlUiExtensionMap().getExtension().put(synchronisation, newlyCreatedPmlUiExtension);
        modelManager.createdPlanElement(Types.SYNCHRONISATION, synchronisation, parentOfElement.getPlan(), false);
    }

    @Override
    public void undoCommand() {
       /* parentOfElement.getPlan().getSynchronisations().remove(synchronisation);
        parentOfElement
                .getPmlUiExtensionMap()
                .getExtension()
                .remove(synchronisation);*/
        parentOfElement.getPmlUiExtensionMap().getExtension().remove(synchronisation, newlyCreatedPmlUiExtension);
        modelManager.createdPlanElement(Types.SYNCHRONISATION, synchronisation, parentOfElement.getPlan(), false);
    }
}
