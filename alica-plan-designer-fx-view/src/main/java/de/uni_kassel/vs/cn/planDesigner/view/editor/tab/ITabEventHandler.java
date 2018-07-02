package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTypeTab.PlanTypeTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.taskRepoTab.TaskRepositoryTab;

public interface ITabEventHandler {
    public abstract void handleTabOpenedEvent(TaskRepositoryTab taskRepositoryTab);
    public abstract void handleTabOpenedEvent(PlanTab planTab);
    public abstract void handleTabOpenedEvent(BehaviourTab behaviourTab);
    public abstract void handleTabOpenedEvent(PlanTypeTab planTypeTab);
}
