package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.common.ViewModelElement;

public interface IEditorTab {
    public abstract ViewModelElement getViewModelElement();
    public abstract GuiModificationEvent handleDelete();
}
