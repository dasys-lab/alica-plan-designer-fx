package de.uni_kassel.vs.cn.planDesigner.ui;

import com.sun.javafx.property.PropertyReference;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Created by marci on 27.11.16.
 */
public class PropertyHBox<T extends PlanElement> extends HBox {
    // TODO resolve problem of not getting value by property reference
    public PropertyHBox(T object,String propertyName) {
        PropertyReference<T> propertyReference = new PropertyReference<>(object.getClass(), propertyName);
        getChildren().addAll(new Text(I18NRepo.getInstance().getString("alicatype.property." + propertyName)),
                new Text(propertyReference.getProperty(propertyName).getBean().toString()));
    }
}
