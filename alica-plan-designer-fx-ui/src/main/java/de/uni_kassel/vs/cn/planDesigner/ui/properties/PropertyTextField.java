package de.uni_kassel.vs.cn.planDesigner.ui.properties;

import javafx.scene.control.TextField;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by marci on 26.02.17.
 */
class PropertyTextField<T> extends TextField {
    public PropertyTextField(T object, String propertyName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        setText(BeanUtils.getProperty(object, propertyName));
        textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                BeanUtils.setProperty(object, propertyName, newValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }
}
