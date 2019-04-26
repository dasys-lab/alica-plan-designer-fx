package de.unikassel.vs.alica.planDesigner.events;

import de.unikassel.vs.alica.planDesigner.alicamodel.AbstractPlan;

public interface IModelEventHandler {
    public abstract void handleModelEvent(ModelEvent event);
//    public abstract void handleUiExtensionModelEvent(UiExtensionModelEvent event);
    public abstract void handleCloseTab(long id);
    public abstract void disableUndo(boolean disable);
    public abstract void disableRedo(boolean disable);
    public abstract void handleNoTaskRepositoryNotification();
    public abstract String getGeneratedFilesForAbstractPlan(AbstractPlan abstractPlan);
}
