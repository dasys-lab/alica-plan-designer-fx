package de.uni_kassel.vs.cn.planDesigner.ui;

import de.uni_kassel.vs.cn.planDesigner.alica.configuration.Configuration;
import de.uni_kassel.vs.cn.planDesigner.common.FileWrapper;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by marci on 16.10.16.
 */
public final class PLDFileTreeView extends TreeView<FileWrapper> {

    private MainController controller;

    public PLDFileTreeView() {
        super(new VirtualDirectoryTreeItem());
        this.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue instanceof PLDTreeItem) {
                if (newValue.getValue().unwrap().isDirectory()) {
                    newValue.setExpanded(!newValue.isExpanded());
                } else {
                    controller.openFile(newValue.getValue().unwrap());
                }
            }
        });
        this.setShowRoot(false);
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }
}

class VirtualDirectoryTreeItem extends TreeItem<FileWrapper> {
    private static final Configuration configuration = new Configuration();

    VirtualDirectoryTreeItem() {
        super();
        this.getChildren().add(new PLDTreeItem(FileWrapper.wrap(configuration.getPlansPath()),
                new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("images/folder24x24.png")))));
        this.getChildren().add(new PLDTreeItem(FileWrapper.wrap(configuration.getRolesPath()),
                new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("images/folder24x24.png")))));
        this.getChildren().add(new PLDTreeItem(FileWrapper.wrap(configuration.getMiscPath()),
                new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("images/folder24x24.png")))));
    }
}
