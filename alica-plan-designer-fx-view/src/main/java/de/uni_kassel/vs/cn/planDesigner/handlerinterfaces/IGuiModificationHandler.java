package de.uni_kassel.vs.cn.planDesigner.handlerinterfaces;

import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.PlanTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.behaviourTab.BehaviourTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTypeTab.PlanTypeTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.taskRepoTab.TaskRepositoryTab;
import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.view.model.BehaviourViewModel;

import java.util.ArrayList;

public interface IGuiModificationHandler {
    public void handle(GuiModificationEvent event);
    public ArrayList<ViewModelElement> getUsages(ViewModelElement viewModelElement);
    public ViewModelElement getViewModelElement(long id);
    public BehaviourViewModel getBehaviourViewModel(long id);
    public abstract void handleTabOpenedEvent(TaskRepositoryTab taskRepositoryTab);
    public abstract void handleTabOpenedEvent(PlanTab planTab);
    public abstract void handleTabOpenedEvent(BehaviourTab behaviourTab);
    public abstract void handleTabOpenedEvent(PlanTypeTab planTypeTab);
}
