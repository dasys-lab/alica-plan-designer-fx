package de.unikassel.vs.alica.planDesigner.view.model;

import javafx.collections.ObservableList;

public interface HasVariableBinding {
    ObservableList<VariableBindingViewModel> getVariableBindings();
    void addVariableBinding(VariableBindingViewModel binding);
    void removeVariableBinding(VariableBindingViewModel binding);
}
