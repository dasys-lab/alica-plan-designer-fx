package de.uni_kassel.vs.cn.planDesigner.events;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;

public class ModelEvent {
    protected ModelQueryType type;
    protected PlanElement oldElement;
    protected PlanElement newElement;

    public ModelEvent (ModelQueryType type, PlanElement oldElement, PlanElement newElement) {
        this.type = type;
        this.oldElement = oldElement;
        this.newElement = newElement;
    }

    public ModelQueryType getType() {
        return type;
    }

    public PlanElement getOldElement() {
        return oldElement;
    }

    public PlanElement getNewElement() {
        return newElement;
    }
}
