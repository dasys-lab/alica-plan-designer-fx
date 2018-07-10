package de.uni_kassel.vs.cn.planDesigner.command.change;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import org.apache.commons.beanutils.BeanUtils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;

public class ChangeAttributeValue<T> extends AbstractCommand {

    private String attribute;
    protected PlanElement planElement;
    protected PlanElement parentPlanElement;

    private T newValue;

    private T oldValue;

    public ChangeAttributeValue(ModelManager modelManager, PlanElement planElement, String attribute, T newValue, PlanElement parentPlanElement) {
        super(modelManager);
        this.planElement = planElement;
        this.attribute = attribute;
        this.newValue = newValue;
        this.parentPlanElement = parentPlanElement;
    }

    @Override
    public void doCommand() {
//        try {
//            oldValue = (T) BeanUtils.getProperty(planElement, attribute);
//            BeanUtils.setProperty(planElement, attribute, newValue);
            if (attribute.equals("masterPlan")) {
                // TODO: what has to be done, in case of changing the masterPlan flag?
            }
            if (attribute.equals("name")) {
                Path path = null;
                if(planElement instanceof Plan) {
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
            if(attribute.equals("activated")) {
                oldValue = (T)(Object)((AnnotatedPlan) planElement).isActivated();
                ((AnnotatedPlan) planElement).setActivated((Boolean) newValue);
            }

            // TODO: Check all other attributes of all model objects...

//        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//            throw new RuntimeException(e);
//        }
    }

    private File getNewFilePath(Path path) {
        return new File(path.toFile().getAbsolutePath().replace("/" + (path.getFileName()
                                .toString().substring(0, path.getFileName().toString().lastIndexOf('.'))) + ".",
                "/" + ((String) newValue) + "."));
    }

    @Override
    public void undoCommand() {
//        try {
//            BeanUtils.setProperty(planElement, attribute, oldValue);
//        } catch (IllegalAccessException | InvocationTargetException e) {
//            throw new RuntimeException(e);
//        }
    }
}
