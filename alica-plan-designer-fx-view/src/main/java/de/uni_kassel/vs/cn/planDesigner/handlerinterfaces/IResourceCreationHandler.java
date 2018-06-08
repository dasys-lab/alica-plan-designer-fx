package de.uni_kassel.vs.cn.planDesigner.handlerinterfaces;

import de.uni_kassel.vs.cn.planDesigner.events.ResourceCreationEvent;

public interface IResourceCreationHandler {

    public void handle(ResourceCreationEvent event);
}
