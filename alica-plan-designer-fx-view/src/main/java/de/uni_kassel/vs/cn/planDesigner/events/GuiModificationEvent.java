package de.uni_kassel.vs.cn.planDesigner.events;

public class GuiModificationEvent {

    // mandatory
    protected GuiEventType eventType;
    protected String elementType;
    protected String name;

    // optional depending from type of event
    protected String absoluteDirectory;
    protected long parentId;
    protected long elementId;

    public GuiModificationEvent(GuiEventType eventType, String elementType, String name) {
        this.eventType = eventType;
        this.elementType = elementType;
        this.name = name;
    }

    public GuiEventType getEventType() {
        return eventType;
    }

    public String getElementType() {
        return elementType;
    }

    public String getName() {
        return name;
    }

    public String getAbsoluteDirectory() {
        return absoluteDirectory;
    }

    public void setAbsoluteDirectory(String absoluteDirectory) {
        this.absoluteDirectory = absoluteDirectory;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getElementId() {
        return elementId;
    }

    public void setElementId (long elementId) {
        this.elementId = elementId;
    }

}
