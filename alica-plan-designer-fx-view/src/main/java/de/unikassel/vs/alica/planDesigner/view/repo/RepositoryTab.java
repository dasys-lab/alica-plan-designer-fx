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

    protected ListView<RepositoryHBox> repositoryListView;
    protected Comparator<RepositoryHBox> modelElementComparator;
    protected RepositoryTool repoTool;
    protected IGuiModificationHandler guiModificationHandler;

    public RepositoryTab(String tabTitle, RepositoryTool repoTool) {
        setText(tabTitle);
        this.repoTool = repoTool;
        this.repositoryListView = new ListView();
        this.repositoryListView.setPrefHeight(repositoryListView.getItems().size() * 24 + 2);
        this.setContent(this.repositoryListView);

        modelElementComparator = Comparator.comparing(o -> !o.getViewModelType().equals(Types.MASTERPLAN));
        modelElementComparator = modelElementComparator.thenComparing(o -> o.getViewModelName());
    }


    public void setGuiModificationHandler(IGuiModificationHandler guiModificationHandler) {
        this.guiModificationHandler = guiModificationHandler;
    }

    public void removeElement(ViewModelElement viewModel) {
        Iterator<RepositoryHBox> iter = repositoryListView.getItems().iterator();
        while(iter.hasNext()) {
            RepositoryHBox repositoryHBox = iter.next();
            if (repositoryHBox.getViewModelId() == viewModel.getId()) {
                iter.remove();
            }
        }
        repositoryListView.setPrefHeight(repositoryListView.getItems().size() * 24 + 2);
        sort();
    }


    public void addElement(ViewModelElement viewModelElement) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                repositoryListView.getItems().add(new RepositoryHBox(viewModelElement, guiModificationHandler));
                repositoryListView.setPrefHeight(repositoryListView.getItems().size() * 24 + 2);
                sort();
            }
        });
    }

    public void addElements(List<? extends ViewModelElement> viewModelElements) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < viewModelElements.size(); i++) {
                    ViewModelElement viewModelElement = viewModelElements.get(i);
                    repositoryListView.getItems().add(new RepositoryHBox(viewModelElement, guiModificationHandler));
                }
                repositoryListView.setPrefHeight(repositoryListView.getItems().size() * 24 + 2);
                sort();
            }
        });
    }

    public void clearGuiContent() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                repositoryListView.getItems().clear();
                repositoryListView.setPrefHeight(repositoryListView.getItems().size() * 24 + 2);
            }
        });
    }

    public ViewModelElement getSelectedItem() {
        RepositoryHBox repoHBox = repositoryListView.getSelectionModel().getSelectedItem();
        if (repoHBox != null) {
            return repoHBox.getViewModelElement();
        } else {
            return null;
        }
    }

    protected void sort() {
        repositoryListView.getItems().sort(modelElementComparator);
    }

}
