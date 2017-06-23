package de.uni_kassel.vs.cn.planDesigner.ui.properties;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.ChangeAttributeValue;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import javafx.scene.control.TextField;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by marci on 26.02.17.
 */
class PropertyTextField<T extends PlanElement> extends TextField {

    private CommandStack commandStack;

    public PropertyTextField(T object, String propertyName, CommandStack commandStack) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        this.commandStack = commandStack;
        setText(BeanUtils.getProperty(object, propertyName));
        textProperty().addListener((observable, oldValue, newValue) -> getCommandStack().storeAndExecute(
                new ChangeAttributeValue<>(object, propertyName, newValue, object)));
    }

    public CommandStack getCommandStack() {
        return commandStack;
    }
}
