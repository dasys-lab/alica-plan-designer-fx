package de.uni_kassel.vs.cn.planDesigner.command.add;


import de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronization;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PmlUiExtension;

public class AddSynchronisationToPlan extends AbstractCommand {

    private PlanModelVisualisationObject parentOfElement;
    private PmlUiExtension newlyCreatedPmlUiExtension;

    public AddSynchronisationToPlan(ModelManager manager, Synchronization element, PlanModelVisualisationObject parentOfElement, PmlUiExtension extension) {
        super(manager, element, parentOfElement.getPlan());
        this.parentOfElement = parentOfElement;
        this.newlyCreatedPmlUiExtension = extension;
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlan().getSynchronizations().add((Synchronization) getElementToEdit());
        parentOfElement
                .getPmlUiExtensionMap()
                .getExtension()
                .put(getElementToEdit(), newlyCreatedPmlUiExtension);
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlan().getSynchronizations().remove(getElementToEdit());
        parentOfElement
                .getPmlUiExtensionMap()
                .getExtension()
                .remove(getElementToEdit());
    }

    @Override
    public String getCommandString() {
        return "Add Synchronisation to Plan.";
    }

    public PmlUiExtension getNewlyCreatedPmlUiExtension() {
        return newlyCreatedPmlUiExtension;
    }
}
