package de.unikassel.vs.alica.planDesigner.view.filebrowser;

import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
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
            File file = new File(path);
            Image folderImage = new AlicaIcon("folder", AlicaIcon.Size.BIG);
            FileTreeItem treeItem = new FileTreeItem(file, new ImageView(folderImage), null);
            this.getChildren().add(treeItem);
            return treeItem;
        }
        return null;
    }
}