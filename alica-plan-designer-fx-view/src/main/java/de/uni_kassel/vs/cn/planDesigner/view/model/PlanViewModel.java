package de.uni_kassel.vs.cn.planDesigner.view.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class PlanViewModel extends PlanElementViewModel {

    protected final BooleanProperty masterPlan = new SimpleBooleanProperty();
    protected final DoubleProperty utilityThreshold = new SimpleDoubleProperty();

    public PlanViewModel(long id, String name, String type) {
        super(id, name, type);
    }

    public final BooleanProperty masterPlanProperty() {return masterPlan; }
    public void setMasterPlan(boolean masterPlan) {
        this.masterPlan.setValue(masterPlan);
    }
    public boolean getMasterPlan() {
        return masterPlan.get();
    }

    public final DoubleProperty utilityThresholdProperty() {return utilityThreshold;}
    public void setUtilityThreshold(double utilityThreshold) {
        this.utilityThreshold.setValue(utilityThreshold);
    }
    public double getUtilityThreshold() {
        return utilityThreshold.get();
    }
}
