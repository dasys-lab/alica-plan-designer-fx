package de.uni_kassel.vs.cn.planDesigner.ui.filebrowser;

import de.uni_kassel.vs.cn.planDesigner.ui.menu.NewResourceMenu;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;

import java.io.File;

/**
 * Created by marci on 08.03.17.
 */
public class PLDFileTreeViewContextMenu extends ContextMenu {

    private File hintFile;

    public PLDFileTreeViewContextMenu() {
        getItems().add(createNewMenu());
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
    }

    public File getHintFile() {
        return hintFile;
    }
}
