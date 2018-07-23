package de.uni_kassel.vs.cn.planDesigner.command.change;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.events.ModelEvent;
import de.uni_kassel.vs.cn.planDesigner.events.ModelEventType;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.FileSystemUtil;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.Types;
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
            BeanUtils.setProperty(planElement, attribute, newValue);
            changeAttribute();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void undoCommand() {
        try {
            BeanUtils.setProperty(planElement, attribute, oldValue);
            changeAttribute();
        } catch (IllegalAccessException | InvocationTargetException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void changeAttribute() throws IOException {
        String absoluteDirectory = modelManager.getAbsoluteDirectory(planElement);
        ModelEvent event = null;
        if (attribute.equals("name")) {
            if (planElement instanceof Plan) {
                moveFile(absoluteDirectory, FileSystemUtil.PLAN_ENDING);

                if (((Plan) planElement).getMasterPlan()) {
                    event = new ModelEvent(ModelEventType.ELEMENT_ATTRIBUTE_CHANGED, null, planElement, Types.MASTERPLAN);
                } else {
                    event = new ModelEvent(ModelEventType.ELEMENT_ATTRIBUTE_CHANGED, null, planElement, Types.PLAN);
                }

                // TODO:
                // 1. pmlex-file
            }

            if (planElement instanceof PlanType) {
                moveFile(absoluteDirectory, FileSystemUtil.PLANTYPE_ENDING);
                event = new ModelEvent(ModelEventType.ELEMENT_ATTRIBUTE_CHANGED, null, planElement, Types.PLANTYPE);
            }

            if (planElement instanceof Behaviour) {
                moveFile(absoluteDirectory, FileSystemUtil.BEHAVIOUR_ENDING);
                event = new ModelEvent(ModelEventType.ELEMENT_ATTRIBUTE_CHANGED, null, planElement, Types.BEHAVIOUR);
            }

            if (planElement instanceof TaskRepository) {
                moveFile(absoluteDirectory, FileSystemUtil.TASKREPOSITORY_ENDING);
                event = new ModelEvent(ModelEventType.ELEMENT_ATTRIBUTE_CHANGED, null, planElement, Types.TASKREPOSITORY);
            }

            if (planElement instanceof Task) {
                event = new ModelEvent(ModelEventType.ELEMENT_ATTRIBUTE_CHANGED, null, planElement, Types.TASK);
            }
        }
        if (attribute.equals("masterPlan")) {
            // TODO: what has to be done, in case of changing the masterPlan flag?
        }
        event.setChangedAttribute(attribute);
        fireModelChangedEvent(event);
    }

    private void moveFile(String absoluteDirectory, String ending) throws IOException {
        File oldFile = FileSystemUtil.getFile(absoluteDirectory, oldValue, ending);
        File newFile = new File(Paths.get(absoluteDirectory, (String) newValue + "." + ending).toString());
        if (newFile.exists()) {
            throw new IOException("ChangeAttributeValue: File " + newFile.toString() + " already exists!");
        }
        if (!oldFile.renameTo(newFile)) {
            throw new IOException("ChangeAttributeValue: Could not rename " + oldFile.toString() + " to " + newFile.toString());
        }
    }

    private void fireModelChangedEvent(ModelEvent event) {
        modelManager.fireEvent(event);
    }

    private File getNewFilePath(Path path) {
        return new File(path.toFile().getAbsolutePath().replace("/" + (path.getFileName()
                        .toString().substring(0, path.getFileName().toString().lastIndexOf('.'))) + ".",
                "/" + ((String) newValue) + "."));
    }
}
