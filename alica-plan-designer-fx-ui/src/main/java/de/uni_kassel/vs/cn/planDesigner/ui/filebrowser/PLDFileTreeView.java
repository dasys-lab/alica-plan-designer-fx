package de.uni_kassel.vs.cn.planDesigner.ui.filebrowser;

import de.uni_kassel.vs.cn.planDesigner.alica.configuration.Configuration;
import de.uni_kassel.vs.cn.planDesigner.common.FileWrapper;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

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
        this.setContextMenu(new PLDFileTreeViewContextMenu());
        setEditable(true);
        setCellFactory(new Callback<TreeView<FileWrapper>, TreeCell<FileWrapper>>() {
            @Override
            public TreeCell<FileWrapper> call(TreeView<FileWrapper> param) {
                TreeCell<FileWrapper> fileWrapperTreeCell = new PLDTreeCell(controller.getCommandStack());
                fileWrapperTreeCell.setContextMenu(new PLDFileTreeViewContextMenu());
                fileWrapperTreeCell.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (newValue) {
                            ((PLDFileTreeViewContextMenu)fileWrapperTreeCell.getContextMenu())
                                    .setCommandStack(controller.getCommandStack());
                            ((PLDFileTreeViewContextMenu)fileWrapperTreeCell.getContextMenu())
                                    .setHintFile(fileWrapperTreeCell.getTreeItem().getValue().unwrap());
                        }
                    }
                });

                if(param.getEditingItem() != null) {
                    fileWrapperTreeCell.setText(param.getEditingItem().getValue().unwrap().getName());
                    fileWrapperTreeCell.setGraphic(param.getEditingItem().getGraphic());
                }
                return fileWrapperTreeCell;
            }
        });
        new Thread(new FileWatcherJob(this)).start();
    }

    public synchronized void updateTreeView(WatchEvent.Kind kind, Path child) {

        ((VirtualDirectoryTreeItem) getRoot()).updateDirectory(kind, child);
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

    public void updateDirectory(WatchEvent.Kind kind, Path child) {
        getChildren().forEach(e -> ((PLDTreeItem)e).updateDirectory(kind, child));
    }

    private Image getImageForFileType(File content) {
        Image listItemImage = null;
        if (content.getName().endsWith(".beh")) {
            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/behaviour24x24.png")));
        } else if (content.getName().endsWith(".pml")) {
            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/plan24x24.png")));
        } else if (content.getName().endsWith(".pty")) {
            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/planTyp24x24.png")));
        } else if (content.getName().endsWith("pmlex") || content.getName().startsWith(".")) {
            return null;
        } else {
            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/folder24x24.png")));
        }
        return listItemImage;
    }
}
