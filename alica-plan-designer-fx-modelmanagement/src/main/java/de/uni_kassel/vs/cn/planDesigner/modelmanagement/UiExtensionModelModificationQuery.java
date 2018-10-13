package de.uni_kassel.vs.cn.planDesigner.modelmanagement;

public class UiExtensionModelModificationQuery {

    private String elementType;
    private long elementId;
    private long parentId;

    private int newX;
    private int newY;

    public UiExtensionModelModificationQuery(String elementType, long elementId, long parentId){
        this.elementType = elementType;
        this.elementId = elementId;
        this.parentId = parentId;
    }

    public String getElementType() {
        return elementType;
    }

    public long getElementId() {
        return elementId;
    }

    public long getParentId() {
        return parentId;
    }

    public int getNewX() {
        return newX;
    }

    public void setNewX(int newX){
        this.newX = newX;
    }

    public int getNewY() {
        return newY;
    }

    public void setNewY(int newY){
        this.newY = newY;
    }
}
