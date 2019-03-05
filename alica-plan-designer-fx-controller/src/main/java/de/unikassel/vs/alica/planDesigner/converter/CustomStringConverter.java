package de.unikassel.vs.alica.planDesigner.converter;

import org.apache.commons.beanutils.Converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Convert value to {@link String}.
 *
 * For most {@link Object}s the {@link #toString()}-method is called.<br/>
 * For {@link Collection}s {@link #toString()} is called on all elements separately and they are joined. This allows
 * having some kind of {@link Collection} in the Model while using a single {@link String} in the ViewModels
 * corresponding object, which can be bound to a text-field in the View much easier
 */
public class CustomStringConverter implements Converter {

    @Override
    public <T> T convert(Class<T> type, Object value) {
        if(value instanceof Collection) {
            Collection<?> collection = (Collection<?>) value;
            List<String> strings = new ArrayList<>();
            for(Object obj : collection) {
                strings.add(obj.toString());
            }
            return type.cast(String.join(" ", strings.toArray(new String[0])));
        }
        return type.cast(value.toString());
    }

}
