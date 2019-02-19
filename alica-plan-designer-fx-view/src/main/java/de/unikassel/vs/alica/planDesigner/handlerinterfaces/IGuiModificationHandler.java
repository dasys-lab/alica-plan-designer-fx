package de.unikassel.vs.alica.planDesigner.handlerinterfaces;

import de.unikassel.vs.alica.planDesigner.events.GuiChangePositionEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.behaviourTab.BehaviourTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTypeTab.PlanTypeTab;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import de.unikassel.vs.alica.planDesigner.view.repo.RepositoryViewModel;

import java.util.ArrayList;

public interface IGuiModificationHandler {
    void handle(GuiModificationEvent event);
    void generateCode(GuiModificationEvent event);
    ArrayList<ViewModelElement> getUsages(ViewModelElement viewModelElement);
    ViewModelElement getViewModelElement(long id);
    void handleUndo();
    void handleRedo();
    RepositoryViewModel getRepoViewModel();

    void handleGuiChangePositionEvent(GuiChangePositionEvent event);
}
