package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

public interface ITabEventHandler {
    public abstract void handleTabOpenedEvent(TaskRepositoryTab taskRepositoryTab);
    public abstract void handleTabOpenedEvent(PlanTab planTab);
    public abstract void handleTabOpenedEvent(BehaviourTab behaviourTab);
}
