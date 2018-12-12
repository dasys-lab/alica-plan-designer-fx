package de.unikassel.vs.alica.planDesigner.view.editor.tab;

import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;

public interface IEditorTab {
    public abstract boolean representsViewModelElement(ViewModelElement element);
    public abstract ViewModelElement getViewModelElement();
    public abstract GuiModificationEvent handleDelete();
    public abstract void save();
}
