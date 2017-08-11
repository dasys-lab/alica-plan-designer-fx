package de.uni_kassel.vs.cn.planDesigner.ui.menu;

import de.uni_kassel.vs.cn.planDesigner.common.FileWrapper;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;

public class RenameFileMenuItem extends MenuItem {
    private TreeCell<FileWrapper> treeCell;
    public RenameFileMenuItem() {
        setOnAction(e -> onRename());
    }

    public void onRename() {
        treeCell.startEdit();
    }

    public void setTreeCell(TreeCell<FileWrapper> treeCell) {
        this.treeCell = treeCell;
        if (treeCell.getTreeItem().getValue().unwrap().isDirectory()) {
            this.setDisable(true);
        }
    }
}
