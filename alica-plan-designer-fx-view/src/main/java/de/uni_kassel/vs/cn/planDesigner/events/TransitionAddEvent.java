package de.uni_kassel.vs.cn.planDesigner.events;


public class TransitionAddEvent extends GuiModificationEvent {

    private long newIn;
    private long newOut;

    public TransitionAddEvent(GuiEventType eventType, String elementType, String name) {
        super(eventType, elementType, name);
    }

    public long getNewIn() {
        return newIn;
    }

    public void setNewIn(long newIn) {
        this.newIn = newIn;
    }

    public long getNewOut() {
        return newOut;
    }

    public void setNewOut(long newOut) {
        this.newOut = newOut;
    }
}
