package de.uni_kassel.vs.cn.planDesigner.view.repo;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tools.RepositoryTool;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IShowUsageHandler;
import javafx.application.Platform;
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

    public RepositoryTab(String tabTitle, RepositoryTool repoTool) {
        setText(tabTitle);
        this.repoTool = repoTool;
        this.repositoryListView = new ListView();
        this.setContent(this.repositoryListView);

        modelElementComparator = Comparator.comparing(o -> !o.getViewModelType().equals(Types.MASTERPLAN));
        modelElementComparator = modelElementComparator.thenComparing(o -> o.getViewModelName());
    }

    public void setShowUsageHandler(IShowUsageHandler showUsageHandler) {
        this.showUsageHandler = showUsageHandler;
    }

    public void addElement(ViewModelElement viewModelElement) {
        RepositoryHBox[] removeBeforeAdding = new RepositoryHBox[1];
        removeBeforeAdding[0] = null;
        for (RepositoryHBox item : repositoryListView.getItems()) {
            if (item.getViewModelElement().equals(viewModelElement)) {
                return;
            } else if (item.getViewModelId() == viewModelElement.getId() && !item.getViewModelName().equals(viewModelElement.getName())) {
                removeBeforeAdding[0] = item;
                break;
            }
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(removeBeforeAdding[0] != null) {
                    repositoryListView.getItems().remove(removeBeforeAdding[0]);
                }
                repositoryListView.getItems().add(new RepositoryHBox(viewModelElement, showUsageHandler));
                sort();
            }
        });
    }

    public void addElements(List<ViewModelElement> viewModelElements) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (ViewModelElement viewModelElement : viewModelElements) {
                    repositoryListView.getItems().add(new RepositoryHBox(viewModelElement, showUsageHandler));
                }
                sort();
            }
        });
    }

    public void clearGuiContent() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                repositoryListView.getItems().clear();
            }
        });
    }

    protected void sort() {
        repositoryListView.getItems().sort(modelElementComparator);
    }

}
