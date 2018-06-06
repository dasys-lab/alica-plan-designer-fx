package de.uni_kassel.vs.cn.planDesigner.view.menu;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.TreeCell;

import java.io.File;

/**
 * Created by marci on 08.03.17.
 */
public class FileTreeViewContextMenu extends ContextMenu {

    private File hintFile;
    private DeleteFileMenuItem deleteFileMenuItem;
    private RenameFileMenuItem renameFileMenuItem;
    private TreeCell treeCell;

    public FileTreeViewContextMenu() {
        deleteFileMenuItem = new DeleteFileMenuItem(hintFile);
        renameFileMenuItem = new RenameFileMenuItem();
        getItems().addAll(createNewMenu(), renameFileMenuItem, deleteFileMenuItem);
    }

    private Menu createNewMenu() {
        return new NewResourceMenu() {
            @Override
            protected File getHintFile() {
                return FileTreeViewContextMenu.this.getHintFile();
            }
        };
    }

    public void setHintFile(File hintFile) {
        this.hintFile = hintFile;
        deleteFileMenuItem.setToDelete(hintFile);
    }

    public File getHintFile() {
        return hintFile;
    }

    public TreeCell getTreeCell() {
        return treeCell;
    }

    public void setTreeCell(TreeCell treeCell) {
        this.treeCell = treeCell;
        renameFileMenuItem.setTreeCell(treeCell);
    }
}
