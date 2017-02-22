package de.uni_kassel.vs.cn.planDesigner.ui.properties;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by marci on 27.11.16.
 */
public class PropertyHBox<T extends PlanElement> extends HBox {

    private TextInputControl textField;

    // TODO resolve problem of not getting value by property reference
    public PropertyHBox(T object, String propertyName) {
        try {
            Text text = new Text(I18NRepo.getString("alicatype.property." + propertyName));
            text.setWrappingWidth(100);
            textField = createTextField(object, propertyName);
            getChildren().addAll(text, textField);
            setHgrow(textField, Priority.ALWAYS);
            setHgrow(text, Priority.ALWAYS);
            setMargin(textField,new Insets(0,100,0,50));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    protected TextInputControl createTextField(T object, String propertyName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return new PropertyTextField(object, propertyName);
    }

    static class PropertyTextArea extends TextArea {
        public PropertyTextArea(PlanElement object, String propertyName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
            super(BeanUtils.getProperty(object, propertyName));
            autosize();
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

    private class PropertyTextField extends TextField {
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

}
