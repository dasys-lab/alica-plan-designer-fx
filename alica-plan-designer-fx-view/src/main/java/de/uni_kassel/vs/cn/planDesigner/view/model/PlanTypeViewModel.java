package de.uni_kassel.vs.cn.planDesigner.view.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class PlanTypeViewModel {
    private ObservableList<ViewModelElement> allPlans;
    private ObservableList<PlanViewModelElement> plansInPlanType;

    public PlanTypeViewModel() {
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
}
