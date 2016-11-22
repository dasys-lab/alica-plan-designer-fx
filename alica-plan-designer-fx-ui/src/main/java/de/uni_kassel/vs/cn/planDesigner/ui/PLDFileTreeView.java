package de.uni_kassel.vs.cn.planDesigner.ui;

import de.uni_kassel.vs.cn.planDesigner.alica.configuration.Configuration;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

/**
 * Created by marci on 16.10.16.
 */
public final class PLDFileTreeView extends TreeView<FileWrapper> {

    private MainController controller;

    public PLDFileTreeView() {
        super(new VirtualDirectoryTreeItem());
        this.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue instanceof PLDTreeItem) {
                controller.openFile(newValue.getValue().unwrap());
            }
        });
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }
}

class VirtualDirectoryTreeItem extends TreeItem<FileWrapper> {
    private static final Configuration configuration  = new Configuration();
    public VirtualDirectoryTreeItem() {
        super();
        this.getChildren().add(new PLDTreeItem(replaceBashVariablesWithActualPath(configuration.getPlansPath()),
                new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("images/folder24x24.png")))));
        this.getChildren().add(new PLDTreeItem(replaceBashVariablesWithActualPath(configuration.getRolesPath()),
                new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("images/folder24x24.png")))));
        this.getChildren().add(new PLDTreeItem(replaceBashVariablesWithActualPath(configuration.getMiscPath()),
                new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("images/folder24x24.png")))));
    }

    private FileWrapper replaceBashVariablesWithActualPath(String path) {

        String config_folder_bash_constant = "$DOMAIN_CONFIG_FOLDER";
        if(path.contains(config_folder_bash_constant)) {
            String domain_config_folder = System.getenv("DOMAIN_CONFIG_FOLDER");
            if (domain_config_folder == null) {
                throw new NullPointerException("DOMAIN CONFIG FOLDER NOT SET");
            }

            return new FileWrapper(new File(path.replace(config_folder_bash_constant, domain_config_folder)));
        } else {
            return new FileWrapper(new File(path));
        }
    }
}
