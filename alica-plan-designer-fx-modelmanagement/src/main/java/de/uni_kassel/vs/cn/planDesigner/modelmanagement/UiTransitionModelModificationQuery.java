package de.uni_kassel.vs.cn.planDesigner.modelmanagement;

import de.uni_kassel.vs.cn.planDesigner.events.ModelQueryType;

public class UiTransitionModelModificationQuery extends ModelModificationQuery{

    private long newIn;
    private long newOut;

    public UiTransitionModelModificationQuery(String elementType, long elementId, long parentId) {
        this(ModelQueryType.CHANGE_ELEMENT, elementType, elementId, parentId);
    }

    public UiTransitionModelModificationQuery(ModelQueryType modelQueryType, String elementType, long elementId, long parentId){
        super(modelQueryType);
        this.elementType = elementType;
        this.elementId = elementId;
        this.parentId = parentId;
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
