package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;
import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getPmlUiExtensionModelFactory;

/**
 * Created by marci on 23.02.17.
 */
public class AddEntryPointInPlan extends AbstractCommand<EntryPoint> {
    private final PlanModelVisualisationObject parentOfElement;
    private final PmlUiExtension newlyCreatedPmlUiExtension;

    public AddEntryPointInPlan(PlanModelVisualisationObject parentOfElement) {
        super(getAlicaFactory().createEntryPoint());
        this.parentOfElement = parentOfElement;
        this.newlyCreatedPmlUiExtension = getPmlUiExtensionModelFactory().createPmlUiExtension();
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlan().getEntryPoints().add(getElementToEdit());
        parentOfElement
                .getPmlUiExtensionMap()
                .getExtension()
                .put(getElementToEdit(), newlyCreatedPmlUiExtension);
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlan().getEntryPoints().remove(getElementToEdit());
        //noinspection SuspiciousMethodCalls
        parentOfElement
                .getPmlUiExtensionMap()
                .getExtension()
                .remove(getElementToEdit());
    }

    @Override
    public String getCommandString() {
        return "Create new EntryPoint";
    }

    public PmlUiExtension getNewlyCreatedPmlUiExtension() {
        return newlyCreatedPmlUiExtension;
    }
}
