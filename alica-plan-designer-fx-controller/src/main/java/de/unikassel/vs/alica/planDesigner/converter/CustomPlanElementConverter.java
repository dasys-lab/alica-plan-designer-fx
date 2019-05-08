package de.unikassel.vs.alica.planDesigner.converter;

import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

/**
 * Convert a value to a {@link de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement}.
 *
 * This converts {@link Long}s by searching for a PlanElement with
 * this id or converts {@link String}s by searching for a PlanElement
 * with this file-name
 */
public class CustomPlanElementConverter implements Converter {

    private ModelManager modelManager;

    public CustomPlanElementConverter(ModelManager modelManager){
        this.modelManager = modelManager;
    }

    @Override
    public <T> T convert(Class<T> type, Object value) {
        if(value instanceof Long) {
            return type.cast(modelManager.getPlanElement((Long) value));
        }
        if(value instanceof String) {
            return type.cast(modelManager.getPlanElement((String) value));
        }

        try {
            return type.cast(value);
        }catch (ClassCastException e) {
            throw new ConversionException(e);
        }
    }
}
