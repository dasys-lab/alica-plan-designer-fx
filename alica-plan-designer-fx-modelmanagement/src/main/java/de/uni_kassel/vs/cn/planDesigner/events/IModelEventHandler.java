package de.uni_kassel.vs.cn.planDesigner.events;

public interface IModelEventHandler {
    public abstract void handleModelEvent(ModelEvent event);
    public abstract void handleCloseTab(long id);
    public abstract void disableUndo(boolean disable);
    public abstract void disableRedo(boolean disable);
}
