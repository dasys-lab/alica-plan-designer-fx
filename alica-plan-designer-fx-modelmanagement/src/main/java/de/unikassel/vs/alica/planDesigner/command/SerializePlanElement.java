package de.unikassel.vs.alica.planDesigner.command;

import de.unikassel.vs.alica.planDesigner.alicamodel.SerializablePlanElement;
import de.unikassel.vs.alica.planDesigner.modelmanagement.FileSystemUtil;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

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
            this.modelManager.serialize(planElement, mmq.getElementType());
            hasBeenSaved = true;
        }
    }

    @Override
    public void undoCommand() {
        // NO-OP
    }
}
