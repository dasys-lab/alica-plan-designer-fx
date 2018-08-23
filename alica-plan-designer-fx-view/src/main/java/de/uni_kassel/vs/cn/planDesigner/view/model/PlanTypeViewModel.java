package de.uni_kassel.vs.cn.planDesigner.view.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class PlanTypeViewModel extends PlanElementViewModel {
    private ObservableList<ViewModelElement> allPlans;
    private ObservableList<AnnotatedPlanView> plansInPlanType;

    public PlanTypeViewModel(long id, String name, String type) {
        super(id, name, type);
        allPlans = FXCollections.observableArrayList(new ArrayList<>());
        plansInPlanType = FXCollections.observableArrayList(new ArrayList<>());
    }

    public void addPlanToAllPlans(ViewModelElement plan) {
        for (ViewModelElement element : allPlans) {
            if (element.getId() == plan.getId()) {
                allPlans.remove(element);
                break;
            }
        }
        boolean alreadyInPlanType = false;
        for (AnnotatedPlanView element : plansInPlanType) {
            if (element.getPlanId() == plan.getId()) {
                alreadyInPlanType = true;
                break;
            }
        }
        if (!alreadyInPlanType) {
            allPlans.add(plan);
        }
    }

    public void removePlanFromAllPlans(long id) {
        for (ViewModelElement element : allPlans) {
            if (element.getId() == id) {
                allPlans.remove(element);
                break;
            }
        }
    }

    public void addPlanToPlansInPlanType(AnnotatedPlanView plan) {
        plansInPlanType.add(plan);
    }

    public void removePlanFromPlansInPlanType(long planId) {
        for(AnnotatedPlanView annotatedPlan : plansInPlanType) {
            if(annotatedPlan.getPlanId() == planId) {
                plansInPlanType.remove(annotatedPlan);
                break;
            }
        }
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

    public ObservableList<AnnotatedPlanView> getPlansInPlanType() {
        return plansInPlanType;
    }

    public void setRelativeDirectory(String relativeDirectory) {
        this.relativeDirectory.set(relativeDirectory);
    }

    public String getRelativeDirectory() {
        return this.relativeDirectory.get();
    }


}
