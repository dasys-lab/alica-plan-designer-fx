package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.uiextensionmodel.PmlUiExtension;


public class AddEntryPointInPlan extends AbstractCommand {
    protected PlanModelVisualisationObject parentOfElement;
    protected PmlUiExtension newlyCreatedPmlUiExtension;
    protected ModelManager manager;
    protected EntryPoint entryPoint;

    public AddEntryPointInPlan(ModelManager manager, PlanModelVisualisationObject parentOfElement, EntryPoint entryPoint, PmlUiExtension pmlUiExtension) {
        super(manager);
        this.entryPoint = entryPoint;
        this.parentOfElement = parentOfElement;
        this.newlyCreatedPmlUiExtension = pmlUiExtension;
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlan().getEntryPoints().add(entryPoint);
        for(Plan plan : manager.getPlans()) {
            if(plan.getId() == parentOfElement.getPlan().getId()) {
                plan.getEntryPoints().add(entryPoint);
                break;
            }
        }
        parentOfElement
                .getPmlUiExtensionMap()
                .getExtension()
                .put(entryPoint, newlyCreatedPmlUiExtension);
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlan().getEntryPoints().remove(entryPoint);
        for(Plan plan : manager.getPlans()) {
            if(plan.getId() == parentOfElement.getPlan().getId()) {
                plan.getEntryPoints().remove(entryPoint);
                break;
            }
        }
        //noinspection SuspiciousMethodCalls
        parentOfElement
                .getPmlUiExtensionMap()
                .getExtension()
                .remove(entryPoint);

    }

    public PmlUiExtension getNewlyCreatedPmlUiExtension() {
        return newlyCreatedPmlUiExtension;
    }
}
