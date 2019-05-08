package de.uni_kassel.vs.cn.planDesigner.view.filebrowser;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

class VirtualDirectoryTreeItem extends TreeItem<FileWrapper> {

    VirtualDirectoryTreeItem() {
        super();
        Configuration conf = ConfigurationManager.getInstance().getActiveConfiguration();
        if (conf == null) {
            return;
        }
        if (conf.getPlansPath() != null && !conf.getPlansPath().isEmpty()) {
            this.getChildren().add(new PLDTreeItem(FileWrapper.wrap(conf.getPlansPath()),
                    new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("images/folder24x24.png")))));
        }
        if (conf.getRolesPath() != null && !conf.getRolesPath().isEmpty()) {
            this.getChildren().add(new PLDTreeItem(FileWrapper.wrap(conf.getRolesPath()),
                    new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("images/folder24x24.png")))));
        }
        if (conf.getTasksPath() != null && !conf.getTasksPath().isEmpty()) {
            this.getChildren().add(new PLDTreeItem(FileWrapper.wrap(conf.getTasksPath()),
                    new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("images/folder24x24.png")))));
        }
    }

    public void updateDirectory(WatchEvent.Kind kind, Path child) {
        getChildren().forEach(e -> ((PLDTreeItem) e).updateDirectory(kind, child));
    }
}