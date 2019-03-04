package de.unikassel.vs.alica.planDesigner.command.change;

import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

public class ChangeAttributeValue extends Command {

    private String attribute;
    private PlanElement planElement;
    private String elementType;
    private Object newValue;
    private Object oldValue;

    public ChangeAttributeValue(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.elementType = mmq.getElementType();
        this.planElement = modelManager.getPlanElement(mmq.getElementId());
        this.attribute = mmq.getAttributeName();
        try {
            Class<?> propertyType = PropertyUtils.getPropertyType(planElement, attribute);
            if(PlanElement.class.isAssignableFrom(propertyType)) {
                newValue = modelManager.getPlanElement((Long) mmq.getNewValue());
            }else {
                newValue = mmq.getNewValue();
            }
            this.oldValue = PropertyUtils.getProperty(planElement, attribute);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doCommand() {
        this.modelManager.changeAttribute(planElement, elementType, attribute, newValue, oldValue);
    }

    @Override
    public void undoCommand() {
        this.modelManager.changeAttribute(planElement, elementType, attribute, oldValue, newValue);
    }
}
