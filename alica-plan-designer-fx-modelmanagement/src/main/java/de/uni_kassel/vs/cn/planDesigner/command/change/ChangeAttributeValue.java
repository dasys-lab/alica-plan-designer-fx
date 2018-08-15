package de.uni_kassel.vs.cn.planDesigner.command.change;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.events.ModelEvent;
import de.uni_kassel.vs.cn.planDesigner.events.ModelEventType;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.FileSystemUtil;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelModificationQuery;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.Types;
import org.apache.commons.beanutils.BeanUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;

public class ChangeAttributeValue extends AbstractCommand {

    private String attribute;
    private PlanElement planElement;
    private String elementType;

    private Object newValue;

    private String oldValue;

    public ChangeAttributeValue(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        this.elementType = mmq.getElementType();
        this.planElement = modelManager.getPlanElement(mmq.getElementId());
        this.attribute = mmq.getAttributeName();
        this.newValue = mmq.getNewValue();
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

        if (attribute.equals("name")) {
            switch (elementType) {
                case Types.PLAN:
                    renameFile(absoluteDirectory, FileSystemUtil.PLAN_ENDING);
                    break;
                case Types.PLANTYPE:
                    renameFile(absoluteDirectory, FileSystemUtil.PLANTYPE_ENDING);
                    break;
                case Types.BEHAVIOUR:
                    renameFile(absoluteDirectory, FileSystemUtil.BEHAVIOUR_ENDING);
                    break;
                case Types.TASKREPOSITORY:
                    renameFile(absoluteDirectory, FileSystemUtil.TASKREPOSITORY_ENDING);
                    break;
                default:
                    //NO-OP
                    break;
            }
        }

        ModelEvent event = new ModelEvent(ModelEventType.ELEMENT_ATTRIBUTE_CHANGED, null, planElement, elementType);
        event.setChangedAttribute(attribute);
        modelManager.fireEvent(event);
    }

    private void renameFile(String absoluteDirectory, String ending) throws IOException {
        File oldFile = FileSystemUtil.getFile(absoluteDirectory, oldValue, ending);
        File newFile = new File(Paths.get(absoluteDirectory, (String) newValue + "." + ending).toString());
        if (newFile.exists()) {
            throw new IOException("ChangeAttributeValue: File " + newFile.toString() + " already exists!");
        }
        if (!oldFile.renameTo(newFile)) {
            throw new IOException("ChangeAttributeValue: Could not rename " + oldFile.toString() + " to " + newFile.toString());
        }
    }
}
