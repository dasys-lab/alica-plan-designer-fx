package de.unikassel.vs.alica.planDesigner.command;

import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.events.ModelEvent;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.*;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;

import java.io.File;

/**
 * Parses a given file and adds the resulting object to the corresponding maps of the model manager.
 */
public class ParsePlan extends Command {
    PlanElement oldElement;
    PlanElement newElement;

    public ParsePlan(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        oldElement = null;
        newElement = null;
    }

    @Override
    public void doCommand() {
        // 1. parse file
        // 2. delete already existing object and put new one
        switch (mmq.getElementType()) {
            case Types.PLAN:
            case Types.MASTERPLAN:
                newElement = modelManager.parseFile(FileSystemUtil.getFile(mmq), Plan.class);
                break;
            case Types.PLANTYPE:
                newElement = modelManager.parseFile(FileSystemUtil.getFile(mmq), PlanType.class);
                break;
            case Types.BEHAVIOUR:
                newElement = modelManager.parseFile(FileSystemUtil.getFile(mmq), Behaviour.class);
                break;
            case Types.TASKREPOSITORY:
                newElement = modelManager.parseFile(FileSystemUtil.getFile(mmq), TaskRepository.class);
                break;
            default:
                System.err.println("ParseAbstractPlan: Parsing model eventType " + mmq.getElementType() + " not implemented, yet!");
                return;
        }

        //Add listeners to newElements isDirty-flag
        ((AbstractPlan) newElement).registerDirtyFlag();
        ((AbstractPlan)newElement).dirtyProperty().addListener((observable, oldValue, newValue) -> {
            ModelEvent event = new ModelEvent(ModelEventType.ELEMENT_ATTRIBUTE_CHANGED, newElement
                    , mmq.getElementType());
            event.setChangedAttribute("dirty");
            event.setNewValue(newValue);
            modelManager.fireEvent(event);
        });

        //Searching for an existing element with the same id, because that will be replaced and needs to be stored for undo
        oldElement = modelManager.getPlanElement(newElement.getId());

        if (newElement instanceof Plan ) {

            //If the new element is a Plan, its visualisation has to be loaded as well
            Plan newPlan = (Plan) newElement;
            File uiExtensionFile = FileSystemUtil.getFile(mmq.getAbsoluteDirectory()
                    , mmq.getName(), Extensions.PLAN_UI);
            UiExtension newUiExtension = modelManager.parseFile(uiExtensionFile, UiExtension.class);
            if(newUiExtension != null){
                //If a visualisation was loaded, replace the old one and update the view
//                modelManager.replaceIncompletePlanElementsInPlanModelVisualisationObject(newUiExtension);
                modelManager.getUiExtensionMap().put(mmq.getElementId(), newUiExtension);
            }

            if(newPlan.getMasterPlan()) {
                modelManager.storePlanElement(Types.MASTERPLAN, newElement, false);
            } else {
                modelManager.storePlanElement(Types.PLAN, newElement, false);
            }
        } else {
            modelManager.storePlanElement(mmq.getElementType(), newElement, false);
        }

    }

    @Override
    public void undoCommand() {
        if (oldElement != null) {
            // replace new object with former old one
            if (oldElement instanceof Plan && ((Plan) oldElement).getMasterPlan()) {
                modelManager.storePlanElement(Types.MASTERPLAN, oldElement, false);
            } else {
                modelManager.storePlanElement(mmq.getElementType(), oldElement, false);
            }
        } else {
            // remove new object
            if (newElement instanceof Plan && ((Plan) newElement).getMasterPlan()) {
                modelManager.dropPlanElement(Types.MASTERPLAN, newElement, false);
            } else {
                modelManager.dropPlanElement(mmq.getElementType(), newElement, false);
            }
        }
    }
}
