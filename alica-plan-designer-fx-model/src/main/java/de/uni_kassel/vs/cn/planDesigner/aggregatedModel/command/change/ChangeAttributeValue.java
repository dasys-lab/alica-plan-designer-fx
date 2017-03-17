package de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by marci on 17.03.17.
 */
public class ChangeAttributeValue<T> extends AbstractCommand<PlanElement> {

    private String attribute;

    private Class type;

    private T newValue;

    private T oldValue;

    public ChangeAttributeValue(PlanElement element, String attribute, Class type, T newValue) {
        super(element);
        this.attribute = attribute;
        this.type = type;
        this.newValue = newValue;
    }

    @Override
    public void doCommand() {
        try {
            oldValue = (T) BeanUtils.getProperty(getElementToEdit(), attribute);
            BeanUtils.setProperty(getElementToEdit(), attribute, newValue);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
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
