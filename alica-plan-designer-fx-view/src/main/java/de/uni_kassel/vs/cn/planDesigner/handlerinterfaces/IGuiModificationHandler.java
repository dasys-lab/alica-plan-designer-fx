package de.uni_kassel.vs.cn.planDesigner.handlerinterfaces;

import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;

public interface IGuiModificationHandler {
    public void handle(GuiModificationEvent event);
}
