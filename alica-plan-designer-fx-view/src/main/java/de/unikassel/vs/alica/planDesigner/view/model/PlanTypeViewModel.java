package de.unikassel.vs.alica.planDesigner.view.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlanTypeViewModel extends SerializableViewModel {

    private ObservableList<PlanViewModel> allPlans;
    private ObservableList<AnnotatedPlanView> plansInPlanType;

    public PlanTypeViewModel(long id, String name, String type) {
        super(id, name, type);
        allPlans = FXCollections.observableArrayList();
        plansInPlanType = FXCollections.observableArrayList();
    }

    public void addPlanToAllPlans(PlanViewModel plan) {
        if (!containsPlan(plan.getId())) {
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

    public void clearAllPlans() {
        allPlans.clear();
    }

    public void clearPlansInPlanType() {
        plansInPlanType.clear();
    }

    public ObservableList<PlanViewModel> getAllPlans() {
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

    /**
     * Checks whether the plan is alread in the plantype as annotated plan.
     * @param id
     * @return
     */
    public boolean containsPlan(long id) {
        for (AnnotatedPlanView annotatedPlan : plansInPlanType) {
            if (annotatedPlan.getPlanId() == id) {
                return true;
            }
        }
        return false;
    }
}
