package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PmlUiExtension;


public class AddEntryPointInPlan extends AbstractCommand {
    private final PlanModelVisualisationObject parentOfElement;
    private final PmlUiExtension newlyCreatedPmlUiExtension;

    public AddEntryPointInPlan(PlanModelVisualisationObject parentOfElement) {
        super(getAlicaFactory().createEntryPoint(), parentOfElement.getPlan());
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
