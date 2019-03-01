package de.unikassel.vs.alica.planDesigner.view.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;
import java.util.List;

public class TransitionViewModel extends PlanElementViewModel {

    public static final String INSTATE = "inState";
    public static final String OUTSTATE = "outState";

    protected StateViewModel inState;
    protected StateViewModel outState;
    protected ObjectProperty<ConditionViewModel> preCondition = new SimpleObjectProperty<>(this, "preCondition", null);
    private ObservableList<BendPointViewModel> bendpoints;

    public TransitionViewModel(long id, String name, String type) {
        super(id, name, type);
        this.bendpoints = FXCollections.observableArrayList(new LinkedList<BendPointViewModel>());
    }

    public StateViewModel getInState() {
        return inState;
    }

    public void setInState(StateViewModel inState) {
        this.inState = inState;
    }

    public StateViewModel getOutState() {
        return outState;
    }

    public void setOutState(StateViewModel outState) {
        this.outState = outState;
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
