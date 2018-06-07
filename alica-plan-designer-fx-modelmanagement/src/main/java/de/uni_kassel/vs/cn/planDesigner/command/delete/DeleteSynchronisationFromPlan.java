package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronization;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PmlUiExtension;

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

    @Override
    public String getCommandString() {
        return "Remove Synchronisation " + synchronization.getName() + " from Plan";
    }
}
