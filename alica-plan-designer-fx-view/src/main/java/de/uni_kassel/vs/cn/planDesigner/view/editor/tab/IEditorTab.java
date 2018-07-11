package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;

public interface IEditorTab {
    public abstract boolean representsViewModelElement(ViewModelElement element);
    public abstract ViewModelElement getPresentedViewModelElement();
    public abstract GuiModificationEvent handleDelete();
    public abstract void save();
}
