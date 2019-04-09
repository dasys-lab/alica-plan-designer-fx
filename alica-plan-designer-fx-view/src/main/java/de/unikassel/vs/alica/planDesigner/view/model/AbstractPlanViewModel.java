package de.unikassel.vs.alica.planDesigner.view.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class AbstractPlanViewModel extends SerializableViewModel {

    protected final ObservableList<VariableViewModel> variables = FXCollections.observableArrayList(new ArrayList<>());

    public AbstractPlanViewModel(long id, String name, String type) {
        super(id, name, type);
    }

    public ObservableList<VariableViewModel> getVariables() {
        return variables;
    }
}
