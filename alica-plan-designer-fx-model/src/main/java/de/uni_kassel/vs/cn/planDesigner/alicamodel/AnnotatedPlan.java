package de.uni_kassel.vs.cn.planDesigner.alicamodel;

import javafx.beans.property.SimpleBooleanProperty;

public class AnnotatedPlan extends PlanElement {

    private final SimpleBooleanProperty activated = new SimpleBooleanProperty();

    private Plan plan;

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    @Override
    public String getName() {
        return "Annotated" + plan.getName();
    }

    public boolean isActivated() {
        return activated.get();
    }

    public void setActivated(boolean activated) {
        this.activated.setValue(activated);
    }

    public SimpleBooleanProperty activatedProperty() {
        return this.activated;
    }
}
