package de.uni_kassel.vs.cn.planDesigner.ui.properties;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
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
            getChildren().addAll(new Text(I18NRepo.getInstance().getString("alicatype.property." + propertyName)),
                    new Separator(Orientation.VERTICAL),
                    new Text(BeanUtils.getProperty(object, propertyName)));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
