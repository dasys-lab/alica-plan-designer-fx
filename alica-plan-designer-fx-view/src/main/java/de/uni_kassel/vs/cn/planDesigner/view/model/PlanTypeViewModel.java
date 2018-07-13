package de.uni_kassel.vs.cn.planDesigner.view.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class PlanTypeViewModel extends PlanElementViewModel {
    private ObservableList<ViewModelElement> allPlans;
    private ObservableList<PlanViewModelElement> plansInPlanType;

    public PlanTypeViewModel(long id, String name, String type) {
        super(id, name, type);
        allPlans = FXCollections.observableArrayList(new ArrayList<>());
        plansInPlanType = FXCollections.observableArrayList(new ArrayList<>());
    }

    public void addPlanToAllPlans(ViewModelElement plan) {
        allPlans.add(plan);
    }

    public void addPlanToPlansInPlanType(PlanViewModelElement plan) {
        plansInPlanType.add(plan);
    }

    public void clearAllPlans() {
        allPlans.clear();
    }

    public void clearPlansInPlanType() {
        plansInPlanType.clear();
    }

    public ObservableList<ViewModelElement> getAllPlans() {
        return allPlans;
    }

    public ObservableList<PlanViewModelElement> getPlansInPlanType() {
        return plansInPlanType;
    }

    public void setRelativeDirectory(String relativeDirectory) {
        this.relativeDirectory.set(relativeDirectory);
    }

    public String getRelativeDirectory() {
        return this.relativeDirectory.get();
    }


}
