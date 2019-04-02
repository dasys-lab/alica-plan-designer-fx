package de.unikassel.vs.alica.planDesigner.view.properties.bindings;

import de.unikassel.vs.alica.planDesigner.view.model.HasVariablesView;
import javafx.util.StringConverter;

public class HasVariablesViewStringConverter extends StringConverter<HasVariablesView> {
    @Override
    public String toString(HasVariablesView object) {
        return object.getName();
    }

    @Override
    public HasVariablesView fromString(String string) {
        return null;
    }
}
