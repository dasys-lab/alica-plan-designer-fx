package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.common.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;

public interface IEditorTab {
    public abstract boolean representsViewModelElement(ViewModelElement element);
    public abstract ViewModelElement getViewModelElement();
    public abstract GuiModificationEvent handleDelete();
}
