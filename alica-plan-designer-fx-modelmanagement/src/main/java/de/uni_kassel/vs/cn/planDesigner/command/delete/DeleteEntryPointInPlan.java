package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.State;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PmlUiExtension;

public class DeleteEntryPointInPlan extends AbstractCommand {

    protected State associatedState;
    protected final PlanModelVisualisationObject parentOfElement;
    protected PmlUiExtension pmlUiExtension;
    protected EntryPoint entryPoint;

    public DeleteEntryPointInPlan(ModelManager modelManager, EntryPoint entryPoint, PlanModelVisualisationObject parentOfElement) {
        super(modelManager);
        this.entryPoint = entryPoint;
        this.parentOfElement = parentOfElement;
        this.pmlUiExtension = parentOfElement.getPmlUiExtensionMap().getExtension().get(entryPoint);
        this.associatedState = entryPoint.getState();
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlan().getEntryPoints().remove(entryPoint);
        parentOfElement.getPmlUiExtensionMap().getExtension().remove(pmlUiExtension);
        if (associatedState != null) {
            associatedState.setEntryPoint(null);
        }
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlan().getEntryPoints().add(entryPoint);
        parentOfElement.getPmlUiExtensionMap().getExtension().put(entryPoint, pmlUiExtension);
        if (associatedState != null) {
            associatedState.setEntryPoint(entryPoint);
        }
    }

    @Override
    public String getCommandString() {
        return null;
    }
}
