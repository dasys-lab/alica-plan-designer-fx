package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PmlUiExtension;


public class AddEntryPointInPlan extends AbstractCommand {
    private final PlanModelVisualisationObject parentOfElement;
    private final PmlUiExtension newlyCreatedPmlUiExtension;
    private final ModelManager manager;

    public AddEntryPointInPlan(PlanModelVisualisationObject parentOfElement, EntryPoint entryPoint, PmlUiExtension pmlUiExtension, ModelManager manager) {
        super(entryPoint, parentOfElement.getPlan());
        this.parentOfElement = parentOfElement;
        this.manager = manager;
        this.newlyCreatedPmlUiExtension = pmlUiExtension;
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlan().getEntryPoints().add((EntryPoint) getElementToEdit());
        for(Plan plan : manager.getPlans()) {
            if(plan.getId() == parentOfElement.getPlan().getId()) {
                plan.getEntryPoints().add((EntryPoint) getElementToEdit());
                break;
            }
        }
        parentOfElement
                .getPmlUiExtensionMap()
                .getExtension()
                .put(getElementToEdit(), newlyCreatedPmlUiExtension);
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlan().getEntryPoints().remove(getElementToEdit());
        for(Plan plan : manager.getPlans()) {
            if(plan.getId() == parentOfElement.getPlan().getId()) {
                plan.getEntryPoints().remove(getElementToEdit());
                break;
            }
        }
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
