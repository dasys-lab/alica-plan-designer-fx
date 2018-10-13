package de.uni_kassel.vs.cn.planDesigner.events;

public class GuiChangePositionEvent extends GuiModificationEvent{

    private int newX;
    private int newY;

    public GuiChangePositionEvent(GuiEventType eventType, String elementType, String name) {
        super(eventType, elementType, name);
    }

    public int getNewX(){
        return newX;
    }

    public void setNewX(int newX){
        this.newX = newX;
    }

    public int getNewY(){
        return newY;
    }

    public void setNewY(int newY){
        this.newY = newY;
    }
}
