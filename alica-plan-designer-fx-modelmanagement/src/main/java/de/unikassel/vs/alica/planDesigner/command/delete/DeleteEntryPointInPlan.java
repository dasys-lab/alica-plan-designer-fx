package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.EntryPoint;
import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanUiExtensionPair;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;

public class DeleteEntryPointInPlan extends AbstractCommand {

    protected State associatedState;
    protected final PlanUiExtensionPair parentOfElement;
    protected UiExtension uiExtension;
    protected EntryPoint entryPoint;

    public DeleteEntryPointInPlan(ModelManager modelManager, EntryPoint entryPoint, PlanUiExtensionPair parentOfElement) {
        super(modelManager);
        this.entryPoint = entryPoint;
        this.parentOfElement = parentOfElement;
        this.uiExtension = parentOfElement.getPmlUiExtension(entryPoint);
        this.associatedState = entryPoint.getState();
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlan().getEntryPoints().remove(entryPoint);
        parentOfElement.remove(entryPoint);
        if (associatedState != null) {
            associatedState.setEntryPoint(null);
        }
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlan().getEntryPoints().add(entryPoint);
        parentOfElement.put(entryPoint, uiExtension);
        if (associatedState != null) {
            associatedState.setEntryPoint(entryPoint);
        }
    }
}
