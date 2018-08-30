package de.uni_kassel.vs.cn.planDesigner.events;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;

public class ModelEvent {
    protected ModelEventType eventType;
    protected String elementType;
    protected PlanElement element;
    protected String changedAttribute;
    protected long parentId;

    public ModelEvent(ModelEventType eventType, PlanElement element, String elementType) {
        this.eventType = eventType;
        this.element = element;
        this.elementType = elementType;
    }

    public ModelEventType getEventType() {
        return eventType;
    }

    public PlanElement getElement() {
        return element;
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
