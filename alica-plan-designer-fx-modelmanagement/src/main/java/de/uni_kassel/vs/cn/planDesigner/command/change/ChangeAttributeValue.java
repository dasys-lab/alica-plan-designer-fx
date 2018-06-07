package de.uni_kassel.vs.cn.planDesigner.command.change;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import org.apache.commons.beanutils.BeanUtils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;

public class ChangeAttributeValue<T> extends AbstractCommand {

    private String attribute;

    private T newValue;

    private T oldValue;

    public ChangeAttributeValue(PlanElement element, String attribute, T newValue, PlanElement affectedPlan) {
        super(element, affectedPlan);
        this.attribute = attribute;
        this.newValue = newValue;
    }

    @Override
    public void doCommand() {
        try {
            oldValue = (T) BeanUtils.getProperty(getElementToEdit(), attribute);
            BeanUtils.setProperty(getElementToEdit(), attribute, newValue);
            if (attribute.equals("masterPlan")) {
                // TODO: what has to be done, in case of changing the masterPlan flag?
            }
            if (attribute.equals("name")) {
                Path path = null;
                if(getElementToEdit() instanceof Plan) {
                    // TODO:
                    // 1. Rename plan, pml-file, and pmlex-file
                    // 2. Fire event for updating gui (Repository, FileTreeView, PlanEditor if the plan is currently opened)
                }

                if (getElementToEdit() instanceof PlanType) {
                    // TODO:
                    // 1. Rename plantype, and pty-file
                    // 2. Fire event for updating gui (Repository, FileTreeView, PlanEditor if the planType is currently opened)
                }

                if (getElementToEdit() instanceof Behaviour) {
                    // TODO:
                    // 1. Rename behaviour, and beh-file
                    // 2. Fire event for updating gui (Repository, FileTreeView, PlanEditor if the behaviour is currently opened)
                }

                if (getElementToEdit() instanceof Task) {
                    // TODO:
                    // 1. Rename task
                    // 2. Fire event for updating gui (Repository, PlanEditor if the taskrepository is currently opened)
                }
            }

            // TODO: Check all other attributes of all model objects...

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
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
            BeanUtils.setProperty(getElementToEdit(), attribute, oldValue);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getCommandString() {
        return null;
    }
}