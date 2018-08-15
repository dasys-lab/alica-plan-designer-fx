package de.uni_kassel.vs.cn.planDesigner.view.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class AnnotatedPlanView extends ViewModelElement {

    protected final BooleanProperty activated = new SimpleBooleanProperty();

    public AnnotatedPlanView(long id, String name, String type, boolean activated) {
        super(id, name, type);
        this.activated.setValue(activated);
    }

    public AnnotatedPlanView(ViewModelElement element, boolean activated) {
        super(element.getId(), element.getName(), element.getType());
        this.activated.setValue(activated);

    }

    public final BooleanProperty activatedProperty() {
        return this.activated;
    }

    public boolean isActivated() {
        return this.activated.get();
    }

    public void setActivated(boolean activated) {
        this.activated.setValue(activated);
    }
}
