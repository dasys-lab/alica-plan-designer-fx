package de.uni_kassel.vs.cn.planDesigner.view.menu;

import de.uni_kassel.vs.cn.planDesigner.common.FileWrapper;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;

public class RenameFileMenuItem extends MenuItem {
    private TreeCell<FileWrapper> treeCell;
    public RenameFileMenuItem() {
        super(I18NRepo.getInstance().getString("label.menu.edit.rename"));
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
