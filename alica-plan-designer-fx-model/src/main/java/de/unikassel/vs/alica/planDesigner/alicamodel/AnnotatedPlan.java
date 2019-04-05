package de.unikassel.vs.alica.planDesigner.alicamodel;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class AnnotatedPlan extends PlanElement {

    private final SimpleBooleanProperty activated = new SimpleBooleanProperty();
    private final SimpleObjectProperty<Plan> plan = new SimpleObjectProperty<>();

    @Override
    public String getName() {
        return "Annotated" + plan.get().getName();
    }

    public Plan getPlan() {
        return plan.get();
    }
    public void setPlan(Plan plan) {
        this.plan.set(plan);
    }
    public SimpleObjectProperty<Plan> planProperty(){
        return plan;
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

    public void registerDirtyFlag(ChangeListenerForDirtyFlag listener) {
        this.nameProperty().addListener(listener);
        this.commentProperty().addListener(listener);
        this.activatedProperty().addListener(listener);
        this.planProperty().addListener(listener);
        this.activatedProperty().addListener(listener);
    }
}
