package de.uni_kassel.vs.cn.planDesigner.view.filebrowser;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

class VirtualDirectoryTreeItem extends TreeItem<File> {

    VirtualDirectoryTreeItem() {
        super();
    }

    public FileTreeItem addTopLevelFolder(String path) {
        if (path != null && !path.isEmpty()) {
            FileTreeItem treeItem = new FileTreeItem(new File(path),
                    new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("images/folder24x24.png"))), null);
            this.getChildren().add(treeItem);
            return treeItem;
        }
        return null;
    }
}