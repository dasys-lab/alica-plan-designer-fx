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
            case Types.TASKREPOSITORY:
                modelManager.serializeToDisk(planElement, FileSystemUtil.TASKREPOSITORY_ENDING);
                break;
            default:
                System.err.println("SerializePlanElement: Serialization of quantifierType " + mmq.getElementType() + " not implemented, yet!");
                break;
        }
    }

    @Override
    public void undoCommand() {
        // NO-OP
    }
}
