package de.unikassel.vs.alica.planDesigner.command;

import de.unikassel.vs.alica.planDesigner.alicamodel.SerializablePlanElement;
import de.unikassel.vs.alica.planDesigner.modelmanagement.FileSystemUtil;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;

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
        this.modelManager.moveFile(elementToMove, newAbsoluteDirectory, ending);


    }

    @Override
    public void undoCommand() {
        this.modelManager.moveFile(elementToMove, originalRelativeDirectory, ending);
    }
}
