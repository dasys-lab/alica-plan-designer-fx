package de.unikassel.vs.alica.planDesigner.command.add;


import de.unikassel.vs.alica.planDesigner.alicamodel.Synchronization;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PmlUiExtension;

public class AddSynchronizationToPlan extends AbstractCommand {

    private PlanModelVisualisationObject parentOfElement;
    private PmlUiExtension newlyCreatedPmlUiExtension;
    protected Synchronization synchronization;

    public AddSynchronizationToPlan(ModelManager manager, Synchronization synchronization, PlanModelVisualisationObject parentOfElement, PmlUiExtension extension) {
        super(manager);
        this.synchronization = synchronization;
        this.parentOfElement = parentOfElement;
        this.newlyCreatedPmlUiExtension = extension;
    }

    @Override
    public void doCommand() {
        /*
        parentOfElement.getPlan().getSynchronizations().add(synchronization);
        parentOfElement
                .getPmlUiExtensionMap()
                .getExtension()
                .put(synchronization, newlyCreatedPmlUiExtension);*/
        modelManager.createdPlanElement(Types.SYNCHRONIZATION, synchronization, parentOfElement.getPlan(), false);
    }

    @Override
    public void undoCommand() {
       /* parentOfElement.getPlan().getSynchronizations().remove(synchronization);
        parentOfElement
                .getPmlUiExtensionMap()
                .getExtension()
                .remove(synchronization);*/
        modelManager.createdPlanElement(Types.SYNCHRONIZATION, synchronization, parentOfElement.getPlan(), false);
    }
}
