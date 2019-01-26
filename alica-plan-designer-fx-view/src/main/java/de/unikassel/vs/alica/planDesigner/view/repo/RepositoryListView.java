package de.unikassel.vs.alica.planDesigner.view.repo;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.application.Platform;
import javafx.scene.control.ListView;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class RepositoryListView extends ListView<RepositoryHBox> {

    protected Comparator<RepositoryHBox> modelElementComparator;
    protected IGuiModificationHandler guiModificationHandler;

    public RepositoryListView () {
        super();
        setPrefHeight(getItems().size() * 24 + 2);

        modelElementComparator = Comparator.comparing(o -> !o.getViewModelType().equals(Types.MASTERPLAN));
        modelElementComparator = modelElementComparator.thenComparing(o -> o.getViewModelName());
    }

    public void setGuiModificationHandler(IGuiModificationHandler guiModificationHandler) {
        this.guiModificationHandler = guiModificationHandler;
    }

    public void removeElement(ViewModelElement viewModel) {
        Iterator<RepositoryHBox> iter = getItems().iterator();
        while(iter.hasNext()) {
            RepositoryHBox repositoryHBox = iter.next();
            if (repositoryHBox.getViewModelId() == viewModel.getId()) {
                iter.remove();
            }
        }
        setPrefHeight(getItems().size() * 24 + 2);
        sort();
    }


    public void addElement(ViewModelElement viewModelElement) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getItems().add(new RepositoryHBox(viewModelElement, guiModificationHandler));
                setPrefHeight(getItems().size() * 24 + 2);
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
                    getItems().add(new RepositoryHBox(viewModelElement, guiModificationHandler));
                }
                setPrefHeight(getItems().size() * 24 + 2);
                sort();
            }
        });
    }

    public void clearGuiContent() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getItems().clear();
                setPrefHeight(getItems().size() * 24 + 2);
            }
        });
    }

    public ViewModelElement getSelectedItem() {
        RepositoryHBox repoHBox = getSelectionModel().getSelectedItem();
        if (repoHBox != null) {
            return repoHBox.getViewModelElement();
        } else {
            return null;
        }
    }

    protected void sort() {
        getItems().sort(modelElementComparator);
    }
}
