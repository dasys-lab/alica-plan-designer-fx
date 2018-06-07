package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.State;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PmlUiExtension;

public class DeleteEntryPointInPlan extends AbstractCommand {

    private State associatedState;
    private final PlanModelVisualisationObject parentOfElement;
    private PmlUiExtension pmlUiExtension;

    public DeleteEntryPointInPlan(EntryPoint element, PlanModelVisualisationObject parentOfElement) {
        super(element, parentOfElement.getPlan());
        this.parentOfElement = parentOfElement;
        this.pmlUiExtension = parentOfElement.getPmlUiExtensionMap().getExtension().get(getElementToEdit());
        this.associatedState = element.getState();
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlan().getEntryPoints().remove(getElementToEdit());
        parentOfElement.getPmlUiExtensionMap().getExtension().remove(pmlUiExtension);
        if (associatedState != null) {
            associatedState.setEntryPoint(null);
        }
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlan().getEntryPoints().add((EntryPoint) getElementToEdit());
        parentOfElement.getPmlUiExtensionMap().getExtension().put(getElementToEdit(), pmlUiExtension);
        if (associatedState != null) {
            associatedState.setEntryPoint((EntryPoint) getElementToEdit());
        }
    }

    @Override
    public String getCommandString() {
        return null;
    }
}
