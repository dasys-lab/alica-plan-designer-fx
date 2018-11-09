package de.unikassel.vs.alica.planDesigner.handlerinterfaces;

import de.unikassel.vs.alica.planDesigner.events.GuiChangePositionEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.behaviourTab.BehaviourTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTypeTab.PlanTypeTab;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;

import java.util.ArrayList;

public interface IGuiModificationHandler {
    public void handle(GuiModificationEvent event);
    public ArrayList<ViewModelElement> getUsages(ViewModelElement viewModelElement);
    public ViewModelElement getViewModelElement(long id);
    public abstract void handleTabOpenedEvent(PlanTab planTab);
    public abstract void handleTabOpenedEvent(BehaviourTab behaviourTab);
    public abstract void handleTabOpenedEvent(PlanTypeTab planTypeTab);
    public abstract void handleUndo();
    public abstract void handleRedo();

    void handleGuiChangePositionEvent(GuiChangePositionEvent event);
}
