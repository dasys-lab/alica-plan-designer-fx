package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.common.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTypeTab.PlanTypeTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.taskRepoTab.TaskRepositoryTab;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class EditorTabPane extends TabPane {

    private ITabEventHandler tabEventHandler;
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
                PlanTab planTab = new PlanTab(viewModelElement);
                tabEventHandler.handleTabOpenedEvent(planTab);
                return planTab;
            case Types.TASKREPOSITORY:
            case Types.TASK:
                TaskRepositoryTab taskTab = new TaskRepositoryTab(viewModelElement, this.guiModificationHandler);
                tabEventHandler.handleTabOpenedEvent(taskTab);
                return taskTab;
            case Types.BEHAVIOUR:
                BehaviourTab behaviourTab = new BehaviourTab(viewModelElement);
                tabEventHandler.handleTabOpenedEvent(behaviourTab);
                return behaviourTab;
            case Types.PLANTYPE:
                PlanTypeTab planTypeTab = new PlanTypeTab(viewModelElement);
                tabEventHandler.handleTabOpenedEvent(planTypeTab);
                return planTypeTab;
            default:
                System.err.println("EditorTabPane: Opening tab of elementType " + viewModelElement.getType() + " not implemented!");
                return null;
        }
    }

    public void setTabEventHandler(ITabEventHandler handler) {
        this.tabEventHandler = handler;
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
