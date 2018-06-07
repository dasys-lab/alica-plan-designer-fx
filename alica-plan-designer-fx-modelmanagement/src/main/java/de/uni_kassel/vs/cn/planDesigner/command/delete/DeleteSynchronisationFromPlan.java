package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Synchronisation;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;

/**
 * Created by marci on 19.03.17.
 */
public class DeleteSynchronisationFromPlan extends AbstractCommand<Synchronisation> {

    private PlanModelVisualisationObject parentOfElement;
    private PmlUiExtension pmlUiExtension;

    public DeleteSynchronisationFromPlan(Synchronisation element, PlanModelVisualisationObject parentOfElement) {
        super(element, parentOfElement.getPlan());
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlan().getSynchronisations().remove(getElementToEdit());
        pmlUiExtension = parentOfElement.getPmlUiExtensionMap().getExtension().get(getElementToEdit());
        parentOfElement.getPmlUiExtensionMap().getExtension().remove(pmlUiExtension);
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlan().getSynchronisations().add(getElementToEdit());
        parentOfElement.getPmlUiExtensionMap().getExtension().put(getElementToEdit(), pmlUiExtension);
    }

    @Override
    public String getCommandString() {
        return "Remove Synchronisation " + getElementToEdit().getName() + " from Plan";
    }
}
