package de.uni_kassel.vs.cn.planDesigner.modelmanagement;

import de.uni_kassel.vs.cn.planDesigner.events.ModelQueryType;

public class UiExtensionModelModificationQuery extends ModelModificationQuery{

    private int newX;
    private int newY;

    public UiExtensionModelModificationQuery(String elementType, long elementId, long parentId) {
        this(ModelQueryType.CHANGE_ELEMENT, elementType, elementId, parentId);
    }

    public UiExtensionModelModificationQuery(ModelQueryType modelQueryType, String elementType, long elementId, long parentId){
        super(modelQueryType);
        this.elementType = elementType;
        this.elementId = elementId;
        this.parentId = parentId;
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
