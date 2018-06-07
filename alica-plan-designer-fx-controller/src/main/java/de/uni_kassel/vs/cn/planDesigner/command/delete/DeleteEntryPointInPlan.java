package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.State;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PmlUiExtension;

public class DeleteEntryPointInPlan extends AbstractCommand {

    private State associatedState;
    // was mapping a plan to its pmlex
//  private final PlanModelVisualisationObject parentOfElement;
    private Plan parentOfElement;
    private PmlUiExtension pmlUiExtension;

    public DeleteEntryPointInPlan(EntryPoint element, Plan parentOfElement) {
        super(element, parentOfElement);
        this.parentOfElement = parentOfElement;
        //TODO needs to be replaced since its emf
        //this.pmlUiExtension = parentOfElement.getPmlUiExtensionMap().getExtension().get(getElementToEdit());
        this.associatedState = element.getState();
    }

    @Override
    public void doCommand() {
        parentOfElement.getEntryPoints().remove(getElementToEdit());
        //TODO needs to be replaced since its emf
        //parentOfElement.getPmlUiExtensionMap().getExtension().remove(pmlUiExtension);
        if (associatedState != null) {
            associatedState.setEntryPoint(null);
        }
    }

    @Override
    public void undoCommand() {
        parentOfElement.getEntryPoints().add((EntryPoint) getElementToEdit());
        //TODO needs to be replaced since its emf
        //parentOfElement.getPmlUiExtensionMap().getExtension().put(getElementToEdit(), pmlUiExtension);
        if (associatedState != null) {
            associatedState.setEntryPoint((EntryPoint) getElementToEdit());
        }
    }

    @Override
    public String getCommandString() {
        return null;
    }
}
