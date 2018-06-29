package de.uni_kassel.vs.cn.planDesigner.events;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;

public class ModelEvent {
    protected ModelQueryType eventType;
    protected String elementType;
    protected PlanElement oldElement;
    protected PlanElement newElement;

    public ModelEvent (ModelQueryType eventType, PlanElement oldElement, PlanElement newElement, String elementType) {
        this.eventType = eventType;
        this.oldElement = oldElement;
        this.newElement = newElement;
        this.elementType = elementType;
    }

    public ModelQueryType getEventType() {
        return eventType;
    }

    public PlanElement getOldElement() {
        return oldElement;
    }

    public PlanElement getNewElement() {
        return newElement;
    }

    public String getElementType() {
        return elementType;
    }
}
