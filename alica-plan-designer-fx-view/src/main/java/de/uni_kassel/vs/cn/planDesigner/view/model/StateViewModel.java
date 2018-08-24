package de.uni_kassel.vs.cn.planDesigner.view.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class StateViewModel extends PlanElementViewModel {

    protected ObservableList<PlanElementViewModel> planElements;
    protected ObservableList<TransitionViewModel> inTransitions;
    protected ObservableList<TransitionViewModel> outTransitions;
    protected EntryPointViewModel entryPoint;

    public StateViewModel(long id, String name, String type) {
        super(id, name, type);
        this.planElements =  FXCollections.observableArrayList(new ArrayList<>());
        this.inTransitions =  FXCollections.observableArrayList(new ArrayList<>());
        this.outTransitions =  FXCollections.observableArrayList(new ArrayList<>());
    }

    public ObservableList<PlanElementViewModel> getPlanElements() {
        return planElements;
    }

    public void setPlanElements(ObservableList<PlanElementViewModel> planElements) {
        this.planElements = planElements;
    }

    public ObservableList<TransitionViewModel> getInTransitions() {
        return inTransitions;
    }

    public void setInTransitions(ObservableList<TransitionViewModel> inTransitions) {
        this.inTransitions = inTransitions;
    }

    public ObservableList<TransitionViewModel> getOutTransitions() {
        return outTransitions;
    }

    public void setOutTransitions(ObservableList<TransitionViewModel> outTransitions) {
        this.outTransitions = outTransitions;
    }

    public EntryPointViewModel getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(EntryPointViewModel entryPoint) {
        this.entryPoint = entryPoint;
    }
}
