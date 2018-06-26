package de.uni_kassel.vs.cn.planDesigner.command;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.FileSystemUtil;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelModificationQuery;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.Types;

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
        switch (modelModificationQuery.getModelElementType()) {
            case Types.PLAN:
                newElement = modelManager.parseFile(FileSystemUtil.getFile(modelModificationQuery), Plan.class);
                modelManager.addPlanElement(newElement, modelModificationQuery.getModelElementType());
                break;
            default:
                System.err.println("ParseAbstractPlan: Parsing model type " + modelModificationQuery.getModelElementType() + " not implemented, yet!");
                return;
        }
    }

    @Override
    public void undoCommand() {
        if (oldElement != null) {
            // replace new object with former old one
            modelManager.addPlanElement(oldElement, modelModificationQuery.getModelElementType());
        } else {
            // remove new object
            modelManager.removePlanElement(newElement, modelModificationQuery.getModelElementType());
        }
    }
}
