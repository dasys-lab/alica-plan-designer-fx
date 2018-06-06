package de.uni_kassel.vs.cn.planDesigner.modelmanagement;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;

public class ModelEvent {
    protected ModelEventType type;
    protected PlanElement oldElement;
    protected PlanElement newElement;

    public ModelEvent (ModelEventType type, PlanElement oldElement, PlanElement newElement) {
        this.type = type;
        this.oldElement = oldElement;
        this.newElement = newElement;
    }

    public ModelEventType getType() {
        return type;
    }

    public PlanElement getOldElement() {
        return oldElement;
    }

    public PlanElement getNewElement() {
        return newElement;
    }
}
