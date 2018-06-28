package de.uni_kassel.vs.cn.planDesigner.command;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.*;

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
                newElement = modelManager.parseFile(FileSystemUtil.getFile(modelModificationQuery), Plan.class);
                modelManager.replaceIncompleteTasks((Plan)newElement);
                break;
            case Types.PLANTYPE:
                newElement = modelManager.parseFile(FileSystemUtil.getFile(modelModificationQuery), PlanType.class);
                break;
            default:
                System.err.println("ParseAbstractPlan: Parsing model type " + modelModificationQuery.getElementType() + " not implemented, yet!");
                return;
        }
        modelManager.addPlanElement(newElement, modelModificationQuery.getElementType(), false);
    }

    @Override
    public void undoCommand() {
        if (oldElement != null) {
            // replace new object with former old one
            modelManager.addPlanElement(oldElement, modelModificationQuery.getElementType(), false);
        } else {
            // remove new object
            modelManager.removePlanElement(newElement, modelModificationQuery.getElementType(), false);
        }
    }
}
