package de.unikassel.vs.alica.planDesigner.view.editor.tab;

import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.behaviourTab.BehaviourTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTypeTab.PlanTypeTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.taskRepoTab.TaskRepositoryTab;
import de.unikassel.vs.alica.planDesigner.view.model.TaskRepositoryViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class EditorTabPane extends TabPane {

    private IGuiModificationHandler guiModificationHandler;

    public EditorTabPane() {
        super();
    }

    public void openTab(ViewModelElement viewModelElement) {
        // find tab if it already opened
        Tab openedTab = null;
        for (Tab tab : getTabs()) {
            if (((IEditorTab) tab).representsViewModelElement(viewModelElement)) {
                openedTab = tab;
            }
        }

        // create new tab if not already opened
        if (openedTab == null) {
            openedTab = createNewTab(viewModelElement);
            getTabs().add(openedTab);
        }

        // make it the selected tab
        getSelectionModel().select(openedTab);
        this.requestFocus();
        this.autosize();
    }

    private Tab createNewTab(ViewModelElement viewModelElement) {
        switch (viewModelElement.getType()) {
            case Types.MASTERPLAN:
            case Types.PLAN:
//                return new PlanTab(guiModificationHandler.getViewModelElement(viewModelElement.getId()), this.guiModificationHandler);
               return new PlanTab(viewModelElement, this.guiModificationHandler);
            case Types.TASKREPOSITORY:
//                return new TaskRepositoryTab((TaskRepositoryViewModel) guiModificationHandler.getViewModelElement(viewModelElement.getId()), this.guiModificationHandler);
                return new TaskRepositoryTab(viewModelElement, this.guiModificationHandler);
            case Types.TASK:
                return new TaskRepositoryTab((TaskRepositoryViewModel) guiModificationHandler.getViewModelElement(viewModelElement.getParentId()), this.guiModificationHandler);
            case Types.BEHAVIOUR:
//                return new BehaviourTab((BehaviourViewModel) guiModificationHandler.getViewModelElement(viewModelElement.getId()), this.guiModificationHandler);
                return new BehaviourTab(viewModelElement, this.guiModificationHandler);
            case Types.PLANTYPE:
//                return new PlanTypeTab((PlanTypeViewModel) guiModificationHandler.getViewModelElement(viewModelElement.getId()), this.guiModificationHandler);
                return new PlanTypeTab(viewModelElement, this.guiModificationHandler);
            default:
                System.err.println("EditorTabPane: Opening tab of elementType " + viewModelElement.getType() + " not implemented!");
                return null;
        }
    }

    public void setGuiModificationHandler(IGuiModificationHandler handler) {
        this.guiModificationHandler = handler;
    }

    public GuiModificationEvent handleDelete() {
        Tab selectedTab = getSelectionModel().getSelectedItem();
        if (selectedTab == null) {
            return null;
        }

        return ((IEditorTab) selectedTab).handleDelete();
    }
}
