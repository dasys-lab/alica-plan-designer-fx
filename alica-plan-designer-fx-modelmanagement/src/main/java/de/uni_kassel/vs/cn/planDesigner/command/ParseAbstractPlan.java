package de.uni_kassel.vs.cn.planDesigner.command;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.*;

import java.util.ArrayList;

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
                replaceIncompleteTasks();
                break;
            case Types.PLANTYPE:
                newElement = modelManager.parseFile(FileSystemUtil.getFile(modelModificationQuery), PlanType.class);
                break;
            default:
                System.err.println("ParseAbstractPlan: Parsing model type " + modelModificationQuery.getModelElementType() + " not implemented, yet!");
                return;
        }
        modelManager.addPlanElement(newElement, modelModificationQuery.getModelElementType(), false);
    }

    private void replaceIncompleteTasks() {
        ArrayList<Task> incompleteTasks = ParsedModelReferences.getInstance().incompleteTasks;
        for (EntryPoint ep : ((Plan)newElement).getEntryPoints()) {
            if (incompleteTasks.contains(ep.getTask())) {
                for (Task task : modelManager.getTaskRepository().getTasks()) {
                    if (task.getId() == ep.getTask().getId()) {
                        ep.setTask(task);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void undoCommand() {
        if (oldElement != null) {
            // replace new object with former old one
            modelManager.addPlanElement(oldElement, modelModificationQuery.getModelElementType(), false);
        } else {
            // remove new object
            modelManager.removePlanElement(newElement, modelModificationQuery.getModelElementType(), false);
        }
    }
}
