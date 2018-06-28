package de.uni_kassel.vs.cn.planDesigner.view.menu;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;

import java.io.File;

public class RenameFileMenuItem extends MenuItem {
    private TreeCell<File> treeCell;
    public RenameFileMenuItem() {
        super(I18NRepo.getInstance().getString("label.menu.edit.rename"));
        setOnAction(e -> onRename());
    }

    public void onRename() {
        treeCell.startEdit();
    }

    public void setTreeCell(TreeCell<File> treeCell) {
        this.treeCell = treeCell;
        if (treeCell.getTreeItem().getValue().isDirectory()) {
            this.setDisable(true);
        }
    }
}
