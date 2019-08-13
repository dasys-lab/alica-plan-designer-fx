package de.unikassel.vs.alica.planDesigner.command.change;

import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.command.ChangeAttributeCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.FileSystemUtil;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class RenameFileTreeFolder extends ChangeAttributeCommand {
    private Object newValue;
    private Object oldValue;
    private String elementType;
    private String ending;


    public RenameFileTreeFolder(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.oldValue = mmq.getAttributeName();
        this.newValue = mmq.getNewValue();
    }
    @Override
    public void doCommand() {
        for (PlanElement planElement: this.modelManager.getPlanElements()) {
            // Not implement for RoleSet
            if(planElement instanceof Plan || planElement instanceof Behaviour || planElement instanceof  TaskRepository
                    || planElement instanceof PlanType) {
                if ( modelManager.getAbsoluteDirectory(planElement).contains(oldValue.toString())) {
                    try {
                        File newFile = new File(newValue.toString());
                        Files.createDirectories(newFile.toPath());
                        if(planElement instanceof Plan) { elementType = Types.PLAN; }
                        if(planElement instanceof PlanType) { elementType = Types.PLANTYPE; }
                        if(planElement instanceof Behaviour) { elementType = Types.BEHAVIOUR; }
                        if(planElement instanceof TaskRepository) { elementType = Types.TASKREPOSITORY; }
                        ending = FileSystemUtil.getExtension((SerializablePlanElement) planElement);
                        this.modelManager.moveFile(((SerializablePlanElement) planElement),elementType, newValue.toString(), ending);
                        fireEvent(((SerializablePlanElement) planElement), elementType, "relativeDirectory");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
//        File oldFile = new File(oldValue.toString());
//        oldFile.delete();
    }
    @Override
    public void undoCommand() {
    }
}
