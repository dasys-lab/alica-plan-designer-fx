package de.unikassel.vs.alica.planDesigner.events;

public class GuiChangeAttributeEvent extends GuiModificationEvent {

    protected String attributeName; // name of the variable in the model element
    protected String attributeType; // type of the variable in the model element
    protected Object newValue; // new value to be assigned to the variable
    protected Object oldValue; // old value of the variable

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

    public Object getOldValue() { return oldValue; }

    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }
}
