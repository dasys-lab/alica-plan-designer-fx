package de.uni_kassel.vs.cn.planDesigner.modelmanagement;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;

import java.io.File;
import java.nio.file.Paths;

public class FileSystemUtil {

    public static final String PLAN_ENDING = "pml";
    public static final String TASKREPOSITORY_ENDING = "tsk";
    public static final String BEHAVIOUR_ENDING = "beh";
    public static final String PLANTYPE_ENDING = "pty";
    public static final String CAPABILITY_DEFINITION_ENDING = "cdefset";
    public static final String ROLES_DEFINITION_ENDING = "rdefset";
    public static final String ROLESET_GRAPH_ENDING = "graph";
    public static final String ROLESET_ENDING = "rset";


    public static File getFile(ModelModificationQuery mmq) {
        switch (mmq.getElementType()) {
            case Types.PLAN:
                return Paths.get(mmq.getAbsoluteDirectory(), mmq.getName() + "." + PLAN_ENDING).toFile();
            case Types.PLANTYPE:
                return Paths.get(mmq.getAbsoluteDirectory(), mmq.getName() + "." + PLANTYPE_ENDING).toFile();
            default:
                System.err.println("FileSystemUtil: Unknown type gets ignored!");
                return null;
        }
    }

    /**
     * Determines the elementType string corresponding to the given PlanElement.
     *
     * @param planElement whose elementType is to be determined
     * @return elementType of the plan element
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
