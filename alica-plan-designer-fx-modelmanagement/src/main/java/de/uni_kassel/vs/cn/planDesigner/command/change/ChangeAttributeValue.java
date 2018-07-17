package de.uni_kassel.vs.cn.planDesigner.command.change;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.FileSystemUtil;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import org.apache.commons.beanutils.BeanUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChangeAttributeValue<T> extends AbstractCommand {

    private String attribute;
    protected PlanElement planElement;
    protected PlanElement parentPlanElement;

    private T newValue;

    private String oldValue;

    public ChangeAttributeValue(ModelManager modelManager, PlanElement planElement, String attribute, T newValue, PlanElement parentPlanElement) {
        super(modelManager);
        this.planElement = planElement;
        this.attribute = attribute;
        this.newValue = newValue;
        this.parentPlanElement = parentPlanElement;
    }

    @Override
    public void doCommand() {
        try {
            oldValue = BeanUtils.getProperty(planElement, attribute);
            if (attribute.equals("name")) {
                BeanUtils.setProperty(planElement, attribute, newValue);
                if(planElement instanceof Plan) {
                    String absoluteDirectory = modelManager.getAbsoluteDirectory(planElement);
                    File oldFile = FileSystemUtil.getFile(absoluteDirectory, planElement.getName(), FileSystemUtil.PLAN_ENDING);
                    File newFile = new File(Paths.get(absoluteDirectory, (String)newValue + "." + FileSystemUtil.PLAN_ENDING).toString());
                    if(newFile.exists()) {
                        throw new IOException("ChangeAttributeValue: File " + newFile.toString() + " already exists!");
                    }
                    if(!oldFile.renameTo(newFile)) {
                        throw new IOException("ChangeAttributeValue: Could not rename " + oldFile.toString() + " to " + newFile.toString());
                    }

                    // TODO:
                    // 1. Rename plan, pml-file, and pmlex-file
                    // 2. Fire event for updating gui (Repository, FileTreeView, PlanEditor if the plan is currently opened)
                }

                if (planElement instanceof PlanType) {
                    // TODO:
                    // 1. Rename plantype, and pty-file
                    // 2. Fire event for updating gui (Repository, FileTreeView, PlanEditor if the planType is currently opened)
                }

                if (planElement instanceof Behaviour) {
                    // TODO:
                    // 1. Rename behaviour, and beh-file
                    // 2. Fire event for updating gui (Repository, FileTreeView, PlanEditor if the behaviour is currently opened)
                }

                if (planElement instanceof Task) {
                    // TODO:
                    // 1. Rename taskToDelete
                    // 2. Fire event for updating gui (Repository, PlanEditor if the taskrepository is currently opened)
                }
            }
            if (attribute.equals("masterPlan")) {
                // TODO: what has to be done, in case of changing the masterPlan flag?
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | IOException e) {
            throw new RuntimeException(e);

        }
    }

    private File getNewFilePath(Path path) {
        return new File(path.toFile().getAbsolutePath().replace("/" + (path.getFileName()
                                .toString().substring(0, path.getFileName().toString().lastIndexOf('.'))) + ".",
                "/" + ((String) newValue) + "."));
    }

    @Override
    public void undoCommand() {
        try {
            BeanUtils.setProperty(planElement, attribute, oldValue);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
