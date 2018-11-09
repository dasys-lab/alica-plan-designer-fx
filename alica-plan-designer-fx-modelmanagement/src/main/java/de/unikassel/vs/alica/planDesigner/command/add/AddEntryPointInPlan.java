package de.unikassel.vs.alica.planDesigner.command.add;

import de.unikassel.vs.alica.planDesigner.alicamodel.EntryPoint;
import de.unikassel.vs.alica.planDesigner.alicamodel.Plan;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanModelVisualisationObject;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PmlUiExtension;


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
