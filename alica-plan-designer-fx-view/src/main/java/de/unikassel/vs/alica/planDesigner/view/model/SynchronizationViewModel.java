package de.unikassel.vs.alica.planDesigner.view.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class SynchronizationViewModel extends PlanElementViewModel {

    protected ObservableList<TransitionViewModel> transitions;

    public SynchronizationViewModel(long id, String name, String type) {
        super(id, name, type);
        this.transitions = FXCollections.observableArrayList(new ArrayList<>());
    }

    public ObservableList<TransitionViewModel> getTransitions() {
        return transitions;
    }
}
