package de.unikassel.vs.alica.planDesigner.view.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TransitionViewModel extends PlanElementViewModel {
    protected SimpleObjectProperty<StateViewModel> inState = new SimpleObjectProperty<>(this, "inState", null);
    protected SimpleObjectProperty<StateViewModel> outState = new SimpleObjectProperty<>(this, "outState", null);
    protected SimpleObjectProperty<ConditionViewModel> preCondition = new SimpleObjectProperty<>(this, "preCondition", null);
    private ObservableList<BendPointViewModel> bendpoints;

    public TransitionViewModel(long id, String name, String type) {
        super(id, name, type);
        this.bendpoints = FXCollections.observableArrayList(new LinkedList<BendPointViewModel>());

        this.uiPropertyList.clear();
        this.uiPropertyList.addAll(Arrays.asList("name", "id", "comment", "relativeDirectory", "instate", "outState", "preCondition"));
    }

    public StateViewModel getInState() {
        return inState.getValue();
    }

    public void setInState(StateViewModel inState) {
        this.inState.setValue(inState);
    }

    public StateViewModel getOutState() {
        return outState.getValue();
    }

    public void setOutState(StateViewModel outState) {
        this.outState.setValue(outState);
    }

    public ConditionViewModel getPreCondition() {
        return preCondition.get();
    }

    public void setPreCondition(ConditionViewModel preCondition) {
        this.preCondition.set(preCondition);
    }

    public ObjectProperty<ConditionViewModel> preConditionProperty(){
        return preCondition;
    }

    public ObservableList<BendPointViewModel> getBendpoints() {
        return bendpoints;
    }

    public void setBendpoints(List<BendPointViewModel> bendpoints) {
        this.bendpoints = FXCollections.observableArrayList(bendpoints);
    }

    public void addBendpoint(BendPointViewModel bendPointViewModel) {
        bendpoints.add(bendPointViewModel);
    }
}
