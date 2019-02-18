package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.EntryPoint;
import de.unikassel.vs.alica.planDesigner.alicamodel.State;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PmlUiExtension;

public class DeleteEntryPointInPlan extends AbstractCommand {

    protected State associatedState;
    protected final PlanModelVisualisationObject parentOfElement;
    protected PmlUiExtension pmlUiExtension;
    protected EntryPoint entryPoint;

    public DeleteEntryPointInPlan(ModelManager modelManager, EntryPoint entryPoint, PlanModelVisualisationObject parentOfElement) {
        super(modelManager);
        this.entryPoint = entryPoint;
        this.parentOfElement = parentOfElement;
        this.pmlUiExtension = parentOfElement.getPmlUiExtension(entryPoint);
        this.associatedState = entryPoint.getState();
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlan().getEntryPoints().remove(entryPoint);
        parentOfElement.removePlanElement(entryPoint);
        if (associatedState != null) {
            associatedState.setEntryPoint(null);
        }
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlan().getEntryPoints().add(entryPoint);
        parentOfElement.put(entryPoint, pmlUiExtension);
        if (associatedState != null) {
            associatedState.setEntryPoint(entryPoint);
        }
    }
}
