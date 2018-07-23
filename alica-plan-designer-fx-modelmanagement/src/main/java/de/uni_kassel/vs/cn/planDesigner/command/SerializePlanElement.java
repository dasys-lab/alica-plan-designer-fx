package de.uni_kassel.vs.cn.planDesigner.command;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.SerializablePlanElement;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.FileSystemUtil;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelModificationQuery;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.Types;

public class SerializePlanElement extends AbstractCommand {
    ModelModificationQuery mmq;
    SerializablePlanElement planElement;

    public SerializePlanElement (ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        this.mmq = mmq;
        this.planElement = (SerializablePlanElement) modelManager.getPlanElement(mmq.getElementId());
    }

    @Override
    public void doCommand() {
        switch (mmq.getElementType()) {
            case Types.TASK:
            case Types.TASKREPOSITORY:
                modelManager.serializeToDisk(planElement, FileSystemUtil.TASKREPOSITORY_ENDING);
                break;
            case Types.PLANTYPE:
                modelManager.serializeToDisk(planElement, FileSystemUtil.PLANTYPE_ENDING);
                break;
            case Types.PLAN:
                modelManager.serializeToDisk(planElement, FileSystemUtil.PLAN_ENDING);
                break;
            case Types.BEHAVIOUR:
                modelManager.serializeToDisk(planElement, FileSystemUtil.BEHAVIOUR_ENDING);
                break;
            default:
                System.err.println("SerializePlanElement: Serialization of type " + mmq.getElementType() + " not implemented, yet!");
                break;
        }
    }

    @Override
    public void undoCommand() {
        // NO-OP
    }
}
