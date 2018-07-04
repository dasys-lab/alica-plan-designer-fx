package de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTypeTab;

import de.uni_kassel.vs.cn.planDesigner.common.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.controller.PlanTypeWindowController;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiModificationHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class PlanTypeViewModel {
    private ObservableList<ViewModelElement> allPlans;
    private ObservableList<ViewModelElement> plansInPlanType;
    private PlanTypeWindowController planTypeWindowController;

    public PlanTypeViewModel() {
        allPlans = FXCollections.observableArrayList(new ArrayList<>());
        plansInPlanType = FXCollections.observableArrayList(new ArrayList<>());
    }

    public void init(PlanTypeTab planTypeTab, IGuiModificationHandler guiModificationHandler) {
        this.planTypeWindowController = planTypeTab.getController();
        this.planTypeWindowController.setGuiModificationHandler(guiModificationHandler);
        this.planTypeWindowController.initPlanListView(allPlans);
        this.planTypeWindowController.initTableView(plansInPlanType);
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
