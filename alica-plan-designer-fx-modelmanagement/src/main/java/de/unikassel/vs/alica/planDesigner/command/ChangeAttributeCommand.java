package de.unikassel.vs.alica.planDesigner.command;

import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;
import de.unikassel.vs.alica.planDesigner.events.ModelEvent;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

public abstract class ChangeAttributeCommand extends Command {

    public ChangeAttributeCommand(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
    }

    protected void fireEvent(PlanElement planElement, String elementType, String changedAttribute) {

        ModelEvent event = new ModelEvent(ModelEventType.ELEMENT_ATTRIBUTE_CHANGED, planElement, elementType);
        event.setChangedAttribute(changedAttribute);

        try {
            // Using PropertyUtils instead of BeanUtils to get the actual Object and not just its String-representation
            event.setNewValue(PropertyUtils.getProperty(planElement, changedAttribute));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        this.modelManager.fireEvent(event);
    }
}
