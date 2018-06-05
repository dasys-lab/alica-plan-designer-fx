package de.uni_kassel.vs.cn.planDesigner.view.filebrowser;

import de.uni_kassel.vs.cn.planDesigner.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.view.menu.NewResourceMenu;
import de.uni_kassel.vs.cn.planDesigner.view.menu.RenameFileMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.TreeCell;

import java.io.File;

/**
 * Created by marci on 08.03.17.
 */
public class PLDFileTreeViewContextMenu extends ContextMenu {

    private File hintFile;
    private CommandStack commandStack;
    private DeleteFileMenuItem deleteFileMenuItem;
    private RenameFileMenuItem renameFileMenuItem;
    private TreeCell treeCell;

    public PLDFileTreeViewContextMenu() {
        deleteFileMenuItem = new DeleteFileMenuItem(hintFile);
        renameFileMenuItem = new RenameFileMenuItem();
        getItems().addAll(createNewMenu(), renameFileMenuItem, deleteFileMenuItem);
    }

    private Menu createNewMenu() {
        return new NewResourceMenu() {
            @Override
            protected File getHintFile() {
                return PLDFileTreeViewContextMenu.this.getHintFile();
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

    public void setCommandStack(CommandStack commandStack) {
        this.commandStack = commandStack;
        deleteFileMenuItem.setCommandStack(commandStack);
    }

    public CommandStack getCommandStack() {
        return commandStack;
    }

    public TreeCell getTreeCell() {
        return treeCell;
    }

    public void setTreeCell(TreeCell treeCell) {
        this.treeCell = treeCell;
        renameFileMenuItem.setTreeCell(treeCell);
    }
}
