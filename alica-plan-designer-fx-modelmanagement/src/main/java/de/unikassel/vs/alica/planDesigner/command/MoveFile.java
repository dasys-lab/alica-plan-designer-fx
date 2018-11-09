package de.unikassel.vs.alica.planDesigner.command;

import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;
import de.unikassel.vs.alica.planDesigner.alicamodel.SerializablePlanElement;
import de.unikassel.vs.alica.planDesigner.modelmanagement.FileSystemUtil;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;

import java.util.ArrayList;

public class MoveFile extends AbstractCommand {

    private SerializablePlanElement elementToMove;
    private String newAbsoluteDirectory;
    private String originalRelativeDirectory;
    private String ending;


    public MoveFile(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        this.elementToMove = (SerializablePlanElement) modelManager.getPlanElement(mmq.getElementId());
        this.ending = FileSystemUtil.getFileEnding(this.elementToMove);
        this.newAbsoluteDirectory = mmq.getAbsoluteDirectory();
        this.originalRelativeDirectory = this.elementToMove.getRelativeDirectory();
    }

    @Override
    public void doCommand() {
        // 1. Delete file from file system
        modelManager.removeFromDisk(elementToMove, ending, true);

        // 2. Change relative directory property
        elementToMove.setRelativeDirectory(modelManager.makeRelativeDirectory(newAbsoluteDirectory, elementToMove.getName() + "." + ending));

        // 3. Serialize file to file system
        modelManager.serializeToDisk(elementToMove, ending, true);

        // 4. Do the 1-3 for the pmlex file in case of pml files
        //TODO implement once pmlex is supported

        // 5. Update external references to file
        serializeEffectedPlanElements();
    }

    @Override
    public void undoCommand() {
        // 1. Delete file from file system
        modelManager.removeFromDisk(elementToMove, ending, true);

        // 2. Change relative directory property
        elementToMove.setRelativeDirectory(modelManager.makeRelativeDirectory(originalRelativeDirectory, elementToMove.getName() + "." + ending));

        // 3. Serialize file to file system
        modelManager.serializeToDisk(elementToMove, ending, true);

        // 4. Do the same for the pmlex file in case of pml files
        //TODO implement once pmlex is supported

        // 5. Update external references to file
        serializeEffectedPlanElements();
    }

    private void serializeEffectedPlanElements() {
        ArrayList<PlanElement> usages = modelManager.getUsages(elementToMove.getId());
        for (PlanElement planElement : usages) {
            if (planElement instanceof SerializablePlanElement) {
                SerializablePlanElement serializablePlanElement = (SerializablePlanElement) planElement;
                modelManager.serializeToDisk(serializablePlanElement, FileSystemUtil.getFileEnding(serializablePlanElement), true);
            }
        }
    }
}
