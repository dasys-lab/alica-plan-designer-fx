package de.unikassel.vs.alica.planDesigner.view.editor.tab;

import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.behaviourTab.BehaviourTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTypeTab.PlanTypeTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.taskRepoTab.TaskRepositoryTab;
import de.unikassel.vs.alica.planDesigner.view.model.SerializableViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.TaskRepositoryViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class EditorTabPane extends TabPane {

    private IGuiModificationHandler guiModificationHandler;

    public EditorTabPane() {
        super();
    }

    public void openTab(SerializableViewModel serializableViewModel) {
        // find tab if it already opened
        Tab openedTab = null;
        for (Tab tab : getTabs()) {
            if (((IEditorTab) tab).representsViewModelElement(serializableViewModel)) {
                openedTab = tab;
            }
        }

        // create new tab if not already opened
        if (openedTab == null) {
            openedTab = createNewTab(serializableViewModel);
            getTabs().add(openedTab);
        }

        // make it the selected tab
        getSelectionModel().select(openedTab);
        this.requestFocus();
        this.autosize();
    }

    private Tab createNewTab(SerializableViewModel serializableViewModel) {
        switch (serializableViewModel.getType()) {
            case Types.MASTERPLAN:
            case Types.PLAN:
               return new PlanTab(serializableViewModel, this.guiModificationHandler);
            case Types.TASKREPOSITORY:
                return new TaskRepositoryTab(serializableViewModel, this.guiModificationHandler);
            case Types.TASK:
                return new TaskRepositoryTab((SerializableViewModel) guiModificationHandler.getViewModelElement(serializableViewModel.getParentId()), this.guiModificationHandler);
            case Types.BEHAVIOUR:
                return new BehaviourTab(serializableViewModel, this.guiModificationHandler);
            case Types.PLANTYPE:
                return new PlanTypeTab(serializableViewModel, this.guiModificationHandler);
            default:
                System.err.println("EditorTabPane: Opening tab of elementType " + serializableViewModel.getType() + " not implemented!");
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
