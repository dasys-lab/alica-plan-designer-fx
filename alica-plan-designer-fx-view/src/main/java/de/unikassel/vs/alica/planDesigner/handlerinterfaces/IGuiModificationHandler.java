package de.unikassel.vs.alica.planDesigner.handlerinterfaces;

import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import de.unikassel.vs.alica.planDesigner.view.repo.RepositoryViewModel;
import javafx.scene.text.Text;

import java.util.ArrayList;

public interface IGuiModificationHandler {
    void handle(GuiModificationEvent event);
    void generateCode(GuiModificationEvent event, Text generatingText);
    ArrayList<ViewModelElement> getUsages(ViewModelElement viewModelElement);
    ViewModelElement getViewModelElement(long id);
    void handleUndo();
    void handleRedo();
    RepositoryViewModel getRepoViewModel();
    void storeAll();
    long getTaskRepositoryID();
}
