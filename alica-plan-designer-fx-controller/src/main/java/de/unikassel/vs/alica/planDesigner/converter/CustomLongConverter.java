package de.unikassel.vs.alica.planDesigner.converter;

import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

/**
 * Convert value to {@link Long}.
 *
 * If a {@link PlanElement} is converted, its id is returned, otherwise the value is cast to {@link Long}. <br\>
 * <i>IMPORTANT:</i> This does only work for {@link Long}, not for its primitive counterpart long. Do not register this to long,
 * because casting an {@link Object} to long will always produce a {@link ClassCastException}, even if a {@link Long} is
 * cast this way. Use {@link javafx.beans.property.Property}s of type {@link Long} and getter- and setter-methods of
 * type {@link Long}, where needed.
 */
public class CustomLongConverter implements Converter {
    @Override
    public <T> T convert(Class<T> type, Object value) {
        if(value == null){
            return type.cast(0L);
        }
        if(value instanceof PlanElement) {
            return type.cast(((PlanElement) value).getId());
        }
        try {
            return type.cast(value);
        }catch (ClassCastException e) {
            throw new ConversionException(e);
        }
    }
}
