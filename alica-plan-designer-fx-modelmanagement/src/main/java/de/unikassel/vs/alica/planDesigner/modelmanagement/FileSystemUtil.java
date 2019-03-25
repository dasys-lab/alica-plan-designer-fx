package de.unikassel.vs.alica.planDesigner.modelmanagement;

import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiExtension;

import java.io.File;
import java.nio.file.Paths;

public class FileSystemUtil {

    public static File getFile(ModelModificationQuery mmq) {
        switch (mmq.getElementType()) {
            case Types.PLAN:
            case Types.MASTERPLAN:
                return Paths.get(mmq.getAbsoluteDirectory(), mmq.getName() + "." + Extensions.PLAN).toFile();
            case Types.PLANTYPE:
                return Paths.get(mmq.getAbsoluteDirectory(), mmq.getName() + "." + Extensions.PLANTYPE).toFile();
            case Types.BEHAVIOUR:
                return Paths.get(mmq.getAbsoluteDirectory(), mmq.getName() + "." + Extensions.BEHAVIOUR).toFile();
            case Types.TASKREPOSITORY:
                return Paths.get(mmq.getAbsoluteDirectory(), mmq.getName() + "." + Extensions.TASKREPOSITORY).toFile();
            default:
                System.err.println("FileSystemUtil: Unknown eventType gets ignored!");
                return null;
        }
    }

    public static File getFile(String absoluteDirectory, String name, String ending) {
        return Paths.get(absoluteDirectory, name + "." + ending).toFile();
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

    /**
     * Determines the file ending string corresponding to the given SerializablePlanElement.
     *
     * @param planElement whose file ending is to be determined
     * @return file ending of the plan element
     */
    public static String getExtension(SerializablePlanElement planElement) {
        if (planElement instanceof Plan) {
            return Extensions.PLAN;
        } else if (planElement instanceof Behaviour) {
            return Extensions.BEHAVIOUR;
        } else if (planElement instanceof PlanType) {
            return Extensions.PLANTYPE;
        } else if (planElement instanceof TaskRepository) {
            return Extensions.TASKREPOSITORY;
        } else {
            return null;
        }
    }

    public static String getExtension(File file) {
        switch (getFileExtensionInternal(file)) {
            case Extensions.PLAN:
                return Types.PLAN;
            case Extensions.BEHAVIOUR:
                return Types.BEHAVIOUR;
            case Extensions.PLANTYPE:
                return Types.PLANTYPE;
            case Extensions.TASKREPOSITORY:
                return Types.TASKREPOSITORY;
            case Extensions.PLAN_UI:
                return Types.UIEXTENSION;
            default:
                return Types.NOTYPE;
        }
    }

    public static Class getClassType(File modelFile) {
        switch (getFileExtensionInternal(modelFile)) {
            case Extensions.PLAN:
                return Plan.class;
            case Extensions.BEHAVIOUR:
                return Behaviour.class;
            case Extensions.PLANTYPE:
                return PlanType.class;
            case Extensions.TASKREPOSITORY:
                return TaskRepository.class;
            case Extensions.PLAN_UI:
                return UiExtension.class;
            default:
                return null;
        }
    }

    private static String getFileExtensionInternal(File file) {
        String fileAsString = file.toString();
        int pointIdx = fileAsString.lastIndexOf('.');
        if (pointIdx == -1) {
            return Extensions.NO;
        }
        return fileAsString.substring(pointIdx + 1);
    }
}
