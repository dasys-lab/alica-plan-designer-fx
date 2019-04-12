package de.unikassel.vs.alica.planDesigner.view.repo;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.RepositoryTool;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents one tab of the Repository. It should be possible to drag elements of this
 * repository into a plan.
 */
public class RepositoryTab extends Tab {

    protected RepositoryTool repoTool;
    protected RepositoryListView repositoryListView;

    public RepositoryTab(String tabTitle, RepositoryTool repoTool) {
        setText(tabTitle);
        this.repoTool = repoTool;
        this.repositoryListView = new RepositoryListView();

        this.setContent(this.repositoryListView);
    }

    public void setGuiModificationHandler(IGuiModificationHandler guiModificationHandler) {
        this.repositoryListView.setGuiModificationHandler(guiModificationHandler);
    }

    public void removeElement(ViewModelElement viewModelElement) {
        this.repositoryListView.removeElement(viewModelElement);
    }

    public void addElement(ViewModelElement viewModelElement) {
        this.repositoryListView.addElement(viewModelElement);
    }

    public void addElements(List<? extends ViewModelElement> viewModelElements) {
        this.repositoryListView.addElements(viewModelElements);
    }

    public void clearGuiContent() {
        this.repositoryListView.clearGuiContent();
    }

    public ViewModelElement getSelectedItem() {
        return this.repositoryListView.getSelectedItem();
    }
}
