package de.unikassel.vs.alica.planDesigner.events;

import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;

public class ModelEvent {
    protected ModelEventType eventType;
    protected String elementType;
    protected PlanElement element;
    protected long parentId;

    protected String changedAttribute;
    protected Object newValue;

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

    public void setNewValue(Object newValue) {
        this.newValue = newValue;
    }

    public Object getNewValue() {
        return this.newValue;
    }
}
