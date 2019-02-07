package de.unikassel.vs.alica.planDesigner.events;

public class GuiModificationEventExpanded extends GuiModificationEvent{

    protected Long targetID;

    public GuiModificationEventExpanded(GuiEventType eventType, String elementType, String name, Long id) {
        this.eventType = eventType;
        this.elementType = elementType;
        this.name = name;
        this.targetID = id;
    }
    public Long getTargetID() {
        return targetID;
    }

    public void setTargetID(Long targetId) {
        this.targetID = targetId;
    }

}
