package de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTypeTab;

import de.uni_kassel.vs.cn.planDesigner.common.ViewModelElement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class PlanTypeViewModel {
    private ObservableList<ViewModelElement> allPlans;
    private ObservableList<ViewModelElement> plansInPlanType;
    private PlanTypeTab planTypeTab;

    public PlanTypeViewModel() {
        allPlans = FXCollections.observableArrayList(new ArrayList<>());
        plansInPlanType = FXCollections.observableArrayList(new ArrayList<>());
    }

    public void setPlanTypeTab (PlanTypeTab planTypeTab) {
        this.planTypeTab = planTypeTab;
        //TODO handle gui stuff
    }

    public void addPlantypeToAllPlans(ViewModelElement planType) {
        allPlans.add(planType);
    }

    public void addPlantypeToPlansInPlanType(ViewModelElement planType) {
        plansInPlanType.add(planType);
    }

    public void clearAllPlans() {
        allPlans.clear();
    }

    public void clearPlansInPlanType() {
        plansInPlanType.clear();
    }
}
