package de.uni_kassel.vs.cn.planDesigner.command;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.SerializablePlanElement;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.FileSystemUtil;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MoveFile extends AbstractCommand {

    private Path originalPath;
    private Path newPath;
    private AbstractPlan elementToMove;
    private String originalRelativeDirectory;
    private String newRelativeDirectory;
    private String ending;


    public MoveFile(ModelManager modelManager, PlanElement elementToMove, Path originalPath, Path newPath) {
        super(modelManager);
        this.originalPath = originalPath;
        this.newPath = newPath;
        this.elementToMove = (AbstractPlan) elementToMove;
        this.originalRelativeDirectory = this.elementToMove.getRelativeDirectory();
        this.ending = originalPath.toString().substring(originalPath.toString().lastIndexOf(".") + 1);
    }

    @Override
    public void doCommand() {
        try {
            if (originalPath.endsWith("pml")) {
                //TODO implement once pmlex is supported
//                    Files.move(new File(originalPath + "ex").toPath(),
//                            new File(newPath + "ex").toPath());
            }
            Files.delete(originalPath);

            newRelativeDirectory = modelManager.makeRelativePlansDirectory(newPath.toString(), elementToMove.getName() + "." + FileSystemUtil.PLAN_ENDING);
            elementToMove.setRelativeDirectory(newRelativeDirectory);
            serializeEffectedPlanElements();

            modelManager.serializeToDisk(elementToMove, ending, true);
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }
    }

    @Override
    public void undoCommand() {
        try {
            if (originalPath.endsWith("pml")) {
                //TODO implement once pmlex is supported
//                    Files.move(new File(originalPath + "ex").toPath(),
//                            new File(newPath + "ex").toPath());
            }
            Files.delete(newPath);

            elementToMove.setRelativeDirectory(originalRelativeDirectory);
            serializeEffectedPlanElements();

            modelManager.serializeToDisk(elementToMove, ending, true);
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }
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
