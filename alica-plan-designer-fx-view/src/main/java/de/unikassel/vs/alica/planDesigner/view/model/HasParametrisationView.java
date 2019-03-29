package de.unikassel.vs.alica.planDesigner.view.model;

import javafx.collections.ObservableList;

public interface HasParametrisationView {
    ObservableList<ParametrisationViewModel> getParametrisations();
    void addParametrisation(ParametrisationViewModel parametrisation);
    void removeParametrisation(ParametrisationViewModel parametrisation);
}
