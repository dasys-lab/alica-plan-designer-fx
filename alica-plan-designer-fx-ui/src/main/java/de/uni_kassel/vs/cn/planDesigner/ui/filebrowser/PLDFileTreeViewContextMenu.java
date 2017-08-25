package de.uni_kassel.vs.cn.planDesigner.ui.filebrowser;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.ui.menu.DeleteFileMenuItem;
import de.uni_kassel.vs.cn.planDesigner.ui.menu.NewResourceMenu;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;

import java.io.File;

/**
 * Created by marci on 08.03.17.
 */
public class PLDFileTreeViewContextMenu extends ContextMenu {

    private File hintFile;
    private CommandStack commandStack;
    private DeleteFileMenuItem deleteFileMenuItem;

    public PLDFileTreeViewContextMenu() {
        deleteFileMenuItem = new DeleteFileMenuItem(hintFile);
        getItems().addAll(createNewMenu(), deleteFileMenuItem);
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
}
