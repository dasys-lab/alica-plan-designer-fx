package de.uni_kassel.vs.cn.planDesigner.view.repo;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.RepositoryTool;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IShowUsageHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;

import java.util.Comparator;
import java.util.List;

/**
 * This class represents one tab of the Repository. It should be possible to drag elements of this
 * repository into a plan.
 */
public class RepositoryTab extends Tab {

    protected ListView<RepositoryHBox> repositoryListView;
    protected Comparator<RepositoryHBox> modelElementComparator;
    protected RepositoryTool repoTool;
    protected IShowUsageHandler showUsageHandler;
    private I18NRepo i18NRepo;

    public RepositoryTab(String tabTitle, RepositoryTool repoTool) {
        setText(tabTitle);
        this.repoTool = repoTool;
        this.repositoryListView = new ListView();
        this.setContent(this.repositoryListView);
        i18NRepo = I18NRepo.getInstance();

        modelElementComparator = Comparator.comparing(o -> !o.getViewModelType().equals(i18NRepo.getString("alicatype.masterplan")));
        modelElementComparator = modelElementComparator.thenComparing(o -> o.getViewModelName());
    }

    public void setShowUsageHandler(IShowUsageHandler showUsageHandler) {
        this.showUsageHandler = showUsageHandler;
    }

    public void addElement(ViewModelElement viewModelElement) {
        repositoryListView.getItems().add(new RepositoryHBox(viewModelElement, showUsageHandler));
        sort();
    }

    public void addElements(List<ViewModelElement> viewModelElements) {
        for (ViewModelElement viewModelElement : viewModelElements) {
            repositoryListView.getItems().add(new RepositoryHBox(viewModelElement, showUsageHandler));
        }
        sort();
    }

    public void clearGuiContent() {
        repositoryListView.getItems().clear();
    }

    protected void sort() {
        repositoryListView.getItems().sort(modelElementComparator);
    }

}
