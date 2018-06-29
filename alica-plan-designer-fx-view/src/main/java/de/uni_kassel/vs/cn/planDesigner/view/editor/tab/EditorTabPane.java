package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.PlanDesignerApplication;
import de.uni_kassel.vs.cn.planDesigner.controller.UsagesWindowController;
import de.uni_kassel.vs.cn.planDesigner.events.GuiEventType;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.filebrowser.TreeViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.view.menu.ShowUsagesMenuItem;
import de.uni_kassel.vs.cn.planDesigner.view.repo.ViewModelElement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class EditorTabPane extends TabPane {

    private ITabEventHandler tabEventHandler;
    private IGuiModificationHandler guiModificationHandler;

    public EditorTabPane() {
        super();
    }

    public void openTab(TreeViewModelElement treeViewModelElement) {
        // find tab if it already opened
        Tab openedTab = null;
        for (Tab tab : getTabs()) {
            if (((IEditorTab) tab).getViewModelElement().equals(treeViewModelElement)) {
                openedTab = tab;
            }
        }

        // create new tab if not already opened
        if (openedTab == null) {
            openedTab = createNewTab(treeViewModelElement);
            getTabs().add(openedTab);
        }

        // make it the selected tab
        getSelectionModel().select(openedTab);
        this.requestFocus();
        this.autosize();
    }

    private Tab createNewTab(TreeViewModelElement treeViewModelElement) {
        switch (treeViewModelElement.getType()) {
            case Types.MASTERPLAN:
            case Types.PLAN:
                PlanTab planTab = new PlanTab(treeViewModelElement);
                tabEventHandler.handleTabOpenedEvent(planTab);
                return planTab;
            case Types.TASKREPOSITORY:
                TaskRepositoryTab taskTab = new TaskRepositoryTab(treeViewModelElement);
                taskTab.setGuiModificationHandler(this.guiModificationHandler);
                tabEventHandler.handleTabOpenedEvent(taskTab);
                return taskTab;
            case Types.BEHAVIOUR:
                BehaviourTab behaviourTab = new BehaviourTab(treeViewModelElement);
                tabEventHandler.handleTabOpenedEvent(behaviourTab);
                return behaviourTab;
            case Types.PLANTYPE:
                PlanTypeTab planTypeTab = new PlanTypeTab(treeViewModelElement);
                tabEventHandler.handleTabOpenedEvent(planTypeTab);
                return planTypeTab;
            default:
                System.err.println("EditorTabPane: Opening tab of elementType " + treeViewModelElement.getType() + " not implemented!");
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
