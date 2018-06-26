package de.uni_kassel.vs.cn.planDesigner.command.add;


import de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronization;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PmlUiExtension;

public class AddSynchronisationToPlan extends AbstractCommand {

    private PlanModelVisualisationObject parentOfElement;
    private PmlUiExtension newlyCreatedPmlUiExtension;
    protected Synchronization synchronization;

    public AddSynchronisationToPlan(ModelManager manager, Synchronization synchronization, PlanModelVisualisationObject parentOfElement, PmlUiExtension extension) {
        super(manager);
        this.synchronization = synchronization;
        this.parentOfElement = parentOfElement;
        this.newlyCreatedPmlUiExtension = extension;
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlan().getSynchronizations().add(synchronization);
        parentOfElement
                .getPmlUiExtensionMap()
                .getExtension()
                .put(synchronization, newlyCreatedPmlUiExtension);
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlan().getSynchronizations().remove(synchronization);
        parentOfElement
                .getPmlUiExtensionMap()
                .getExtension()
                .remove(synchronization);
    }
}
