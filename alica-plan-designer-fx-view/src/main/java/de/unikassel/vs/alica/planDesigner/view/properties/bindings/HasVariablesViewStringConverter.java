package de.unikassel.vs.alica.planDesigner.view.properties.bindings;

import de.unikassel.vs.alica.planDesigner.view.model.AbstractPlanViewModel;
import javafx.util.StringConverter;

public class HasVariablesViewStringConverter extends StringConverter<AbstractPlanViewModel> {
    @Override
    public String toString(AbstractPlanViewModel object) {
        if (object == null) {
            return "Nothing Selected!";
        }
        return object.getName();
    }

    @Override
    public AbstractPlanViewModel fromString(String string) {
        return null;
    }
}
