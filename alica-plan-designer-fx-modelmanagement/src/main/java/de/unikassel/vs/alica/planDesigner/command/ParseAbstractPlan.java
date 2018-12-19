package de.unikassel.vs.alica.planDesigner.command;

import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.modelmanagement.FileSystemUtil;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PlanModelVisualisationObject;

import java.io.File;

/**
 * Parses a given file and adds the resulting object to the corresponding maps of the model manager.
 */
public class ParseAbstractPlan extends AbstractCommand {
    ModelModificationQuery modelModificationQuery;
    PlanElement oldElement;
    PlanElement newElement;

    public ParseAbstractPlan(ModelManager modelManager, ModelModificationQuery modelModificationQuery) {
        super(modelManager);
        this.modelModificationQuery = modelModificationQuery;
        oldElement = null;
        newElement = null;
    }

    @Override
    public void doCommand() {
        // 1. parse file
        // 2. delete already existing object and add new one
        switch (modelModificationQuery.getElementType()) {
            case Types.PLAN:
            case Types.MASTERPLAN:
                newElement = modelManager.parseFile(FileSystemUtil.getFile(modelModificationQuery), Plan.class);
                modelManager.replaceIncompleteTasksInEntryPoints((Plan) newElement);
                break;
            case Types.PLANTYPE:
                newElement = modelManager.parseFile(FileSystemUtil.getFile(modelModificationQuery), PlanType.class);
                modelManager.replaceIncompletePlansInPlanType((PlanType)newElement);
                break;
            case Types.BEHAVIOUR:
                newElement = modelManager.parseFile(FileSystemUtil.getFile(modelModificationQuery), Behaviour.class);
                break;
            case Types.TASKREPOSITORY:
                newElement = modelManager.parseFile(FileSystemUtil.getFile(modelModificationQuery), TaskRepository.class);
                break;
            default:
                System.err.println("ParseAbstractPlan: Parsing model eventType " + modelModificationQuery.getElementType() + " not implemented, yet!");
                return;
        }

        //Searching for an existing element with the same id, because that will be replaced and needs to be stored for undo
        oldElement = modelManager.getPlanElement(newElement.getId());

        if (newElement instanceof Plan ) {
//            replaceIncompleteElements(newElement);

            //If the new element is a Plan, its visualisation has to be loaded as well
            Plan newPlan = (Plan) newElement;
            File uiExtensionFile = FileSystemUtil.getFile(modelModificationQuery.getAbsoluteDirectory()
                    , modelModificationQuery.getName(), FileSystemUtil.PLAN_EXTENSION_ENDING);
            PlanModelVisualisationObject newPlanModelVisualisationObject = modelManager.parseFile(uiExtensionFile, PlanModelVisualisationObject.class);

            if(newPlanModelVisualisationObject != null){

                //If a visualisation was loaded, replace the old one and update the view
                modelManager.replaceIncompletePlanElementsInPlanModelVisualisationObject(newPlanModelVisualisationObject);
                modelManager.getPlanModelVisualisationObjectMap().put(modelModificationQuery.getElementId(), newPlanModelVisualisationObject);
                modelManager.updatePlanModelVisualisationObject(newPlanModelVisualisationObject);
            }

            if(newPlan.getMasterPlan()) {
                modelManager.createdPlanElement(Types.MASTERPLAN, newElement, null, false);
            } else {
                modelManager.createdPlanElement(Types.PLAN, newElement, null, false);
            }
        } else {
//            replaceIncompleteElements(newElement);
            modelManager.createdPlanElement(modelModificationQuery.getElementType(), newElement, null, false);
        }

    }

    @Override
    public void undoCommand() {
        if (oldElement != null) {
            // replace new object with former old one
            if (oldElement instanceof Plan && ((Plan) oldElement).getMasterPlan()) {
                modelManager.createdPlanElement(Types.MASTERPLAN, oldElement, null, false);
            } else {
                modelManager.createdPlanElement(modelModificationQuery.getElementType(), oldElement, null, false);
            }
        } else {
            // remove new object
            if (newElement instanceof Plan && ((Plan) newElement).getMasterPlan()) {
                modelManager.removedPlanElement(Types.MASTERPLAN, newElement, null, false);
            } else {
                modelManager.removedPlanElement(modelModificationQuery.getElementType(), newElement, null, false);
            }
        }
    }

//    //TODO complete for other PlanElements
//    private void replaceIncompleteElements(PlanElement newElement) {
//        if(newElement instanceof PlanType) {
//            modelManager.replaceIncompletePlansInPlanType((PlanType) newElement);
//        }
//    }
}
