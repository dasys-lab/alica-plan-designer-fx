package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.filebrowser.TreeViewModelElement;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class EditorTabPane extends TabPane {

    public void openTab(TreeViewModelElement treeViewModelElement) {
        // find tab if it already opened
        Tab openedTab = null;
        for (Tab tab : getTabs()) {
            if (((AbstractEditorTab) tab).getTreeViewModelElement().equals(treeViewModelElement)) {
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
    }

    private Tab createNewTab(TreeViewModelElement treeViewModelElement) {
        switch (treeViewModelElement.getType()) {
            case Types.MASTERPLAN:
            case Types.PLAN:
                return new PlanTab(treeViewModelElement);
            case Types.TASKREPOSITORY:
                return new TaskRepositoryTab(treeViewModelElement);
            case Types.BEHAVIOUR:
                System.err.println("EditorTabPane: Opening Behaviours not implemented, yet!");
                return null;
            default:
                return null;
        }
    }
}
