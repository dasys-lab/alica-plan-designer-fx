package de.unikassel.vs.alica.planDesigner.command.change;

import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;
import de.unikassel.vs.alica.planDesigner.command.ChangeAttributeCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

public class ChangeAttributeValue extends ChangeAttributeCommand {

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
            newValue = mmq.getNewValue();
            // Using PropertyUtils instead of BeanUtils to get the actual Object and not just its String-representation
            if (mmq.getOldValue() == null) {
                this.oldValue = PropertyUtils.getProperty(planElement, attribute);
            } else {
                this.oldValue = mmq.getOldValue();
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doCommand() {
        this.modelManager.changeAttribute(planElement, elementType, attribute, newValue, oldValue);
        fireEvent(planElement, elementType, attribute, newValue, oldValue);
    }

    @Override
    public void undoCommand() {
        this.modelManager.changeAttribute(planElement, elementType, attribute, oldValue, newValue);
        fireEvent(planElement, elementType, attribute, oldValue, newValue);
    }
}
