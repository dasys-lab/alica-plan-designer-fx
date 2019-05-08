package de.uni_kassel.vs.cn.planDesigner.view.repo;

import javafx.scene.control.ListView;
import javafx.scene.control.Tab;

import java.util.Comparator;

/**
 * This class represents one tab of the Repository. It should be possible to drag elements of this
 * repository into a plan.
 */
public class RepositoryTab extends Tab {

    protected ListView<RepositoryHBox> repositoryListView;
    protected Comparator<RepositoryHBox> modelElementComparator;
    protected RepositoryTool repoTool;

    public RepositoryTab(String tabTitle, RepositoryTool repoTool) {
        setText(tabTitle);
        this.repoTool = repoTool;
        this.repositoryListView = new ListView();
        this.setContent(this.repositoryListView);

        modelElementComparator = Comparator.comparing(o -> !o.getViewModelType().equals("masterplan"));
        modelElementComparator = modelElementComparator.thenComparing(o -> o.getViewModelName());
    }

    public void addElement(ViewModelElement viewModelElement) {
        repositoryListView.getItems().add(new RepositoryHBox(viewModelElement));
        sort();
    }

    protected void sort() {
        repositoryListView.getItems().sort((Comparator<? super RepositoryHBox>) (Object) modelElementComparator);
    }
}
