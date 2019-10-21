package de.unikassel.vs.alica.planDesigner.view.properties.bindings;

import de.unikassel.vs.alica.planDesigner.view.model.VariableViewModel;
import javafx.util.StringConverter;

public class VariableStringConverter extends StringConverter<VariableViewModel> {
    @Override
    public String toString(VariableViewModel object) {
        if (object == null) {
            return "Nothing Selected!";
        } else {
            return object.getName();
        }
    }

    @Override
    public VariableViewModel fromString(String string) {
        return null;
    }
}
