package de.uni_kassel.vs.cn.planDesigner.ui.properties;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by marci on 27.11.16.
 */
public class PropertyHBox<T extends PlanElement> extends HBox {
    // TODO resolve problem of not getting value by property reference
    public PropertyHBox(T object, String propertyName) {
        try {
            Text text = new Text(I18NRepo.getInstance().getString("alicatype.property." + propertyName));
            text.setWrappingWidth(100);
            PropertyTextField propertyTextField = new PropertyTextField(object, propertyName);
            getChildren().addAll(text, propertyTextField);
            setHgrow(propertyTextField, Priority.ALWAYS);
            setHgrow(text, Priority.ALWAYS);
            setMargin(propertyTextField,new Insets(0,100,0,50));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
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
