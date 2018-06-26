package de.uni_kassel.vs.cn.planDesigner.modelmanagement;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;

import java.io.File;
import java.nio.file.Paths;

public class FileSystemUtil {

    public static String PLAN_ENDING = "pml";
    public static String TASKREPOSITORY_ENDING = "tsk";
    public static String BEHAVIOUR_ENDING = "beh";
    public static String PLANTYPE_ENDING = "pty";

    public static File getFile(ModelModificationQuery mmq) {
        switch (mmq.getModelElementType()) {
            case Types.PLAN:
                return Paths.get(mmq.getAbsoluteDirectory(), mmq.getName() + "." + PLAN_ENDING).toFile();
            default:
                System.err.println("FileSystemUtil: Unknown type gets ignored!");
                return null;
        }
    }

    /**
     * Determines the modelElementType string corresponding to the given PlanElement.
     *
     * @param planElement whose modelElementType is to be determined
     * @return modelElementType of the plan element
     */
    public static String getTypeString(PlanElement planElement) {
        if (planElement instanceof Plan) {
            Plan plan = (Plan) planElement;
            if (plan.getMasterPlan()) {
                return Types.MASTERPLAN;
            } else {
                return Types.PLAN;
            }
        } else if (planElement instanceof Behaviour) {
            return Types.BEHAVIOUR;
        } else if (planElement instanceof PlanType) {
            return Types.PLANTYPE;
        } else if (planElement instanceof Task) {
            return Types.TASK;
        } else if (planElement instanceof TaskRepository) {
            return Types.TASKREPOSITORY;
        } else if (planElement instanceof Role) {
            return Types.ROLE;
        } else {
            return null;
        }
    }
}
