package de.uni_kassel.vs.cn.planDesigner.command;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.SerializablePlanElement;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.FileSystemUtil;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelModificationQuery;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.Types;

public class SerializePlanElement extends AbstractCommand {
    ModelModificationQuery mmq;
    SerializablePlanElement planElement;

    private boolean hasBeenSaved;

    public SerializePlanElement (ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        this.mmq = mmq;
        this.planElement = (SerializablePlanElement) modelManager.getPlanElement(mmq.getElementId());
        hasBeenSaved = false;
    }

    @Override
    public void doCommand() {

        //Prevent confusing behavior in combination with undo and redo.
        //Saving should take place only once and not again via redo, because it would overwrite later safes of the user.
        //Therefore a boolean is used to keep track of whether this is the first time doCommand is called
        if(!hasBeenSaved) {
            switch (mmq.getElementType()) {
                case Types.TASK:
                case Types.TASKREPOSITORY:
                    modelManager.serializeToDisk(planElement, FileSystemUtil.TASKREPOSITORY_ENDING, false);
                    break;
                case Types.PLANTYPE:
                    modelManager.serializeToDisk(planElement, FileSystemUtil.PLANTYPE_ENDING, false);
                    break;
                case Types.PLAN:
                case Types.MASTERPLAN:
                    modelManager.serializeToDisk(planElement, FileSystemUtil.PLAN_ENDING, false);
                    break;
                case Types.BEHAVIOUR:
                    modelManager.serializeToDisk(planElement, FileSystemUtil.BEHAVIOUR_ENDING, false);
                    break;
                default:
                    System.err.println("SerializePlanElement: Serialization of type " + mmq.getElementType() + " not implemented, yet!");
                    break;
            }

            hasBeenSaved = true;
        }
    }

    @Override
    public void undoCommand() {
        // NO-OP
    }
}
