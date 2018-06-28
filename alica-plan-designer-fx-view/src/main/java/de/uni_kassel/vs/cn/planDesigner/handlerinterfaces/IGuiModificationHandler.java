package de.uni_kassel.vs.cn.planDesigner.handlerinterfaces;

import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.view.repo.ViewModelElement;

import java.util.ArrayList;

public interface IGuiModificationHandler {
    public void handle(GuiModificationEvent event);
    public ArrayList<ViewModelElement> getUsages(ViewModelElement viewModelElement);
}
