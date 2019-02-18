package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.EntryPoint;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanUiExtensionPair;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;


public class AddEntryPointInPlan extends AbstractCommand {
    protected PlanUiExtensionPair parentOfElement;
    protected UiExtension newlyCreatedUiExtension;
    protected EntryPoint entryPoint;

    public AddEntryPointInPlan(ModelManager manager, PlanUiExtensionPair parentOfElement, EntryPoint entryPoint, UiExtension uiExtension) {
        super(manager);
        this.entryPoint = entryPoint;
        this.parentOfElement = parentOfElement;
        this.newlyCreatedUiExtension = uiExtension;
    }

    @Override
    public void doCommand() {
        parentOfElement.put(entryPoint, newlyCreatedUiExtension);
        modelManager.createdPlanElement(Types.ENTRYPOINT, entryPoint, entryPoint.getPlan(), false);
    }

    @Override
    public void undoCommand() {
        parentOfElement.remove(entryPoint);
        modelManager.removedPlanElement(Types.ENTRYPOINT, entryPoint, parentOfElement.getPlan(), false);
    }

    public UiExtension getNewlyCreatedUiExtension() {
        return newlyCreatedUiExtension;
    }
}
