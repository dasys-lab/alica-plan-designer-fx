package de.uni_kassel.vs.cn.planDesigner.events;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;

public class ModelEvent {
    protected ModelEventType eventType;
    protected String elementType;
    protected PlanElement oldElement;
    protected PlanElement newElement;
    protected String changedAttribute;
    protected long parentId;

    public ModelEvent (ModelEventType eventType, PlanElement oldElement, PlanElement newElement, String elementType) {
        this.eventType = eventType;
        this.oldElement = oldElement;
        this.newElement = newElement;
        this.elementType = elementType;
    }

    public ModelEventType getEventType() {
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

    public String getChangedAttribute() {
        return changedAttribute;
    }

    public void setChangedAttribute(String changedAttribute) {
        this.changedAttribute = changedAttribute;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
