package de.uni_kassel.vs.cn.planDesigner.view.filebrowser;

import de.uni_kassel.vs.cn.planDesigner.common.FileWrapper;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

class VirtualDirectoryTreeItem extends TreeItem<FileWrapper> {

    VirtualDirectoryTreeItem() {
        super();
    }

    public void updateDirectory(WatchEvent.Kind kind, Path child) {
        getChildren().forEach(e -> ((FileTreeItem) e).updateDirectory(kind, child));
    }

    public void addTopLevelFolder(String path) {
        if (path != null && !path.isEmpty()) {
            this.getChildren().add(new FileTreeItem(FileWrapper.wrap(path),
                    new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("images/folder24x24.png")))));
        }
    }
}