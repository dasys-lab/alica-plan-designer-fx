package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.EntryPoint;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PmlUiExtension;


public class AddEntryPointInPlan extends AbstractCommand {
    protected PlanModelVisualisationObject parentOfElement;
    protected PmlUiExtension newlyCreatedPmlUiExtension;
    protected EntryPoint entryPoint;

    public AddEntryPointInPlan(ModelManager manager, PlanModelVisualisationObject parentOfElement, EntryPoint entryPoint, PmlUiExtension pmlUiExtension) {
        super(manager);
        this.entryPoint = entryPoint;
        this.parentOfElement = parentOfElement;
        this.newlyCreatedPmlUiExtension = pmlUiExtension;
    }

    @Override
    public void doCommand() {
        parentOfElement.getPmlUiExtensionMap().getExtension().put(entryPoint, newlyCreatedPmlUiExtension);
        modelManager.addPlanElement(Types.ENTRYPOINT, entryPoint, entryPoint.getPlan(), false);
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPmlUiExtensionMap().getExtension().remove(entryPoint);
        modelManager.removePlanElement(Types.ENTRYPOINT, entryPoint, parentOfElement.getPlan(), false);
    }

    public PmlUiExtension getNewlyCreatedPmlUiExtension() {
        return newlyCreatedPmlUiExtension;
    }
}
