package de.uni_kassel.vs.cn.planDesigner.view.filebrowser;

import de.uni_kassel.vs.cn.planDesigner.common.FileWrapper;
import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class FileTreeView extends TreeView<FileWrapper> {

    private MainWindowController controller;

    private boolean wasDragged;
    private TreeItem<FileWrapper> draggedItem;
    private Cursor originalCursor;
    private String startFolder;
    private VirtualDirectoryTreeItem virtualDirectoryTreeItem;

    public FileTreeView() {
        super(new VirtualDirectoryTreeItem());
        virtualDirectoryTreeItem = (VirtualDirectoryTreeItem) getRoot();

        addEventHandler(MouseDragEvent.DRAG_DETECTED, e -> {
            System.out.println("Source: " + e.getSource() + " Target: " + e.getTarget());
            originalCursor = getCursor();
            Node node = ((Node) e.getTarget()).getParent();
            if (node instanceof FileTreeCell == false) {
                e.consume();
                return;
            }
            draggedItem = ((FileTreeCell) node).getTreeItem();
            startFolder = draggedItem.getValue().unwrap().toString();
            startFolder = startFolder.substring(0, startFolder.lastIndexOf(File.separator));
            String fileName = draggedItem.getValue().toString();
            if (fileName.endsWith(".beh")) {
                getScene().setCursor(new ImageCursor(new Image(FileTreeView.class.getClassLoader()
                        .getResourceAsStream("images/behaviour24x24.png"))));
            } else if (fileName.endsWith(".pml")) {
//                try {
//                    Plan plan = (Plan) EMFModelUtils.loadAlicaFileFromDisk(draggedItem.getValue().unwrap());
//                    if (plan.isMasterPlan()) {
//                        getScene().setCursor(new ImageCursor(new Image(FileTreeView.class.getClassLoader()
//                                .getResourceAsStream("images/masterplan24x24.png"))));
//                    } else {
                        getScene().setCursor(new ImageCursor(new Image(FileTreeView.class.getClassLoader()
                                .getResourceAsStream("images/plan24x24.png"))));
//                    }
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
            } else if (fileName.endsWith(".pty")) {
                getScene().setCursor(new ImageCursor(new Image(FileTreeView.class.getClassLoader()
                        .getResourceAsStream("images/planTyp24x24.png"))));
            } else if (fileName.endsWith(".tsk")) {
                getScene().setCursor(new ImageCursor(new Image(FileTreeView.class.getClassLoader()
                        .getResourceAsStream("images/tasks24x24.png"))));
            }
            wasDragged = true;
            e.consume();
        });

        addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            getScene().setCursor(originalCursor);
            if (!wasDragged) {
                e.consume();
                return;
            }

            wasDragged = false;

            if (e.getPickResult() == null) {
                e.consume();
                return;
            }

            FileTreeCell treeCell = null;
            if (e.getPickResult().getIntersectedNode().getParent() instanceof Group) {
                for (Node child : e.getPickResult().getIntersectedNode().getParent().getChildrenUnmodifiable()) {
                    if (child.getBoundsInParent().contains(e.getX(), e.getY(), e.getZ())) {
                        treeCell = (FileTreeCell) child;
                        break;
                    }
                }
            } else {
                treeCell = (FileTreeCell) e.getPickResult().getIntersectedNode().getParent();
            }

            File parent = treeCell.getTreeItem().getValue().unwrap();

            if (parent.isDirectory() == false) {
                parent = treeCell.getTreeItem().getParent().getValue().unwrap();
            }

            if (startFolder.equals(parent.toString())) {
                e.consume();
                return;
            }

            try {
                if (draggedItem.getValue().unwrap().getName().endsWith("pml")) {
                    Files.move(new File(draggedItem.getValue().unwrap().toString() + "ex").toPath(),
                            new File(parent, draggedItem.getValue().unwrap().getName() + "ex").toPath());
                }
                Files.move(draggedItem.getValue().unwrap().toPath(),
                        new File(parent, draggedItem.getValue().unwrap().getName()).toPath());
            } catch (IOException e1) {
                throw new RuntimeException(e1);
            }
            e.consume();
        });


        this.setShowRoot(false);
        this.setContextMenu(new PLDFileTreeViewContextMenu());
        setEditable(true);
        setCellFactory(param -> {
            TreeCell<FileWrapper> fileWrapperTreeCell = new FileTreeCell(controller);
            fileWrapperTreeCell.setContextMenu(new PLDFileTreeViewContextMenu());
            fileWrapperTreeCell.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (newValue) {
                        ((PLDFileTreeViewContextMenu) fileWrapperTreeCell.getContextMenu())
                                .setCommandStack(controller.getCommandStack());
                        ((PLDFileTreeViewContextMenu) fileWrapperTreeCell.getContextMenu())
                                .setHintFile(fileWrapperTreeCell.getTreeItem().getValue().unwrap());
                        ((PLDFileTreeViewContextMenu) fileWrapperTreeCell.getContextMenu())
                                .setTreeCell(fileWrapperTreeCell);
                    }
                }
            });

            if (param.getEditingItem() != null) {
                fileWrapperTreeCell.setText(param.getEditingItem().getValue().unwrap().getName());
                fileWrapperTreeCell.setGraphic(param.getEditingItem().getGraphic());
            }
            return fileWrapperTreeCell;
        });
    }

    public void setController(MainWindowController controller) {
        this.controller = controller;
    }

    public void addBehaviour(TreeViewModelElement behaviour) {
        String[] folders = behaviour.getDestinationPath().split(File.pathSeparator);
        TreeItem folder = findFolder(folders, 0, virtualDirectoryTreeItem);
        if (folder != null) {
            File file = Paths.get(behaviour.getDestinationPath(), behaviour.getName(), ".beh").toFile();
            folder.getChildren().add(new FileTreeItem(new FileWrapper(file), new ImageView(getImage(file))));
        }
        //TODO runtime exception
    }

    private TreeItem findFolder(String[] path, int index, TreeItem treeItem) {
        for (Object item : treeItem.getChildren()) {
            TreeItem newItem = (TreeItem)item;
            if (!(newItem.getValue().toString().endsWith(path[index]))) {
                continue;
            }
            if (index == path.length - 1) {
                return newItem;
            }
            return findFolder(path, index + 1, newItem);
        }
        return null;
    }


    private Image getImage(File file) {
        Image listItemImage;
        if (file.getName().endsWith(".beh")) {
            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/behaviour24x24.png")));
        } else if (file.getName().endsWith(".pml")) {
//            try {
//                Plan plan = (Plan)EMFModelUtils.loadAlicaFileFromDisk(content);
//                if (plan.isMasterPlan()) {
//                    listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/masterplan24x24.png")));
//                } else {
            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/plan24x24.png")));
//                }
//            } catch (IOException e1) {
//                e1.printStackTrace();
//                return null;
//            }
        } else if (file.getName().endsWith(".pty")) {
            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/planTyp24x24.png")));
        } else if (file.getName().endsWith(".tsk")) {
            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/tasks24x24.png")));
        } else if (file.isDirectory()) {
            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/folder24x24.png")));
        } else  {
            return null;
        }
        return listItemImage;
    }
}