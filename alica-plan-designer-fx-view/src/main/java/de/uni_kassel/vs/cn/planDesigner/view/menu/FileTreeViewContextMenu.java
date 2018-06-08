package de.uni_kassel.vs.cn.planDesigner.view.menu;

import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeCell;

import java.io.File;

public class FileTreeViewContextMenu extends ContextMenu {

    private File hintFile;
    private DeleteFileMenuItem deleteFileMenuItem;
    private RenameFileMenuItem renameFileMenuItem;
    private NewResourceMenu newResourceMenu;

    private TreeCell treeCell;

    public FileTreeViewContextMenu() {
        deleteFileMenuItem = new DeleteFileMenuItem(hintFile);
        renameFileMenuItem = new RenameFileMenuItem();
        newResourceMenu = new NewResourceMenu(hintFile);
        getItems().addAll(newResourceMenu, renameFileMenuItem, deleteFileMenuItem);
    }

    public void setHintFile(File hintFile) {
        this.hintFile = hintFile;
        deleteFileMenuItem.setToDelete(hintFile);
        newResourceMenu.setInitialDirectoryHint(hintFile);
    }

    public void setTreeCell(TreeCell treeCell) {
        this.treeCell = treeCell;
        renameFileMenuItem.setTreeCell(treeCell);
    }
}
