package de.unikassel.vs.alica.planDesigner.events;

public class GuiChangeAttributeEvent extends GuiModificationEvent {

    protected String attributeName;
    protected Object newValue;
    protected String attributeType;

    public GuiChangeAttributeEvent (GuiEventType eventType, String elementType, String name) {
        super(eventType, elementType, name);
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Object getNewValue() {
        return newValue;
    }

    public void setNewValue(Object newValue) {
        this.newValue = newValue;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }
}
