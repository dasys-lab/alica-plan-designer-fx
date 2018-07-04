package de.uni_kassel.vs.cn.planDesigner.view.filebrowser;

import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.menu.FileTreeViewContextMenu;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public final class FileTreeView extends TreeView<File> {

    private MainWindowController controller;

    private boolean wasDragged;
    private FileTreeItem draggedItem;
    private Cursor originalCursor;
    private String startFolder;
    private VirtualDirectoryTreeItem virtualDirectoryTreeItem;
    private String plansPath;
    private String taskPath;
    private String rolesPath;
    private FileTreeItem plansFileTreeItem;
    private FileTreeItem rolesFileTreeItem;
    private FileTreeItem tasksFileTreeItem;

    public FileTreeView() {
        super(new VirtualDirectoryTreeItem());
        virtualDirectoryTreeItem = (VirtualDirectoryTreeItem) getRoot();

        // Setup Drag support
        addEventHandler(MouseDragEvent.DRAG_DETECTED, e -> {
            originalCursor = getCursor();
            Node node = ((Node) e.getTarget()).getParent();
            if (node instanceof FileTreeCell == false) {
                e.consume();
                return;
            }
            System.out.println("Source: " + e.getSource() + " Target: " + e.getTarget());
            draggedItem = (FileTreeItem) ((FileTreeCell) node).getTreeItem();
            startFolder = draggedItem.getValue().getAbsolutePath();
            startFolder = startFolder.substring(0, startFolder.lastIndexOf(File.separator));
            switch (draggedItem.getViewModelElement().getType()) {
                case Types.BEHAVIOUR:
                    getScene().setCursor(new ImageCursor(new Image(FileTreeView.class.getClassLoader()
                            .getResourceAsStream("images/behaviour24x24.png"))));
                    break;
                case Types.PLAN:
                    getScene().setCursor(new ImageCursor(new Image(FileTreeView.class.getClassLoader()
                            .getResourceAsStream("images/plan24x24.png"))));
                    break;
                case Types.MASTERPLAN:

                    getScene().setCursor(new ImageCursor(new Image(FileTreeView.class.getClassLoader()
                            .getResourceAsStream("images/masterplan24x24.png"))));
                    break;
                case Types.PLANTYPE:
                    getScene().setCursor(new ImageCursor(new Image(FileTreeView.class.getClassLoader()
                            .getResourceAsStream("images/plantype24x24.png"))));
                    break;
                case Types.TASKREPOSITORY:
                    getScene().setCursor(new ImageCursor(new Image(FileTreeView.class.getClassLoader()
                            .getResourceAsStream("images/tasks24x24.png"))));
                    break;
                default:
                    System.err.println("FileTreeView: " + draggedItem.getViewModelElement().getType() + " not handled!");
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

            File parent = treeCell.getTreeItem().getValue();

            if (parent.isDirectory() == false) {
                parent = treeCell.getTreeItem().getParent().getValue();
            }

            String targetFolder = parent.toString();
            if (targetFolder.equals(startFolder) || targetFolder.contains(rolesPath) || targetFolder.contains(taskPath)) {
                startFolder = "";
                e.consume();
                return;
            }

            //TODO fire file moved event
            controller.getMoveFileHandler().moveFile(draggedItem.getViewModelElement().getId(),
                    draggedItem.getValue().toPath(),
                    new File(parent, draggedItem.getValue().getName()).toPath());
            e.consume();
        });


        this.setShowRoot(false);
        this.setContextMenu(new FileTreeViewContextMenu());
        this.setEditable(true);

        setCellFactory(param -> {
            TreeCell<File> fileWrapperTreeCell = new FileTreeCell(controller);
            fileWrapperTreeCell.setContextMenu(new FileTreeViewContextMenu());
            fileWrapperTreeCell.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (newValue) {
                        ((FileTreeViewContextMenu) fileWrapperTreeCell.getContextMenu())
                                .setHintFile(fileWrapperTreeCell.getTreeItem().getValue());
                        ((FileTreeViewContextMenu) fileWrapperTreeCell.getContextMenu())
                                .setTreeCell(fileWrapperTreeCell);
                    }
                }
            });

            if (param.getEditingItem() != null) {
                fileWrapperTreeCell.setText(param.getEditingItem().getValue().getName());
                fileWrapperTreeCell.setGraphic(param.getEditingItem().getGraphic());
            }
            return fileWrapperTreeCell;
        });

        this.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            controller.setDeleteDisabled(newValue == null);
        });
    }

    /**
     * Setter for the {@link MainWindowController}
     *
     * @param controller
     */
    public void setController(MainWindowController controller) {
        this.controller = controller;
    }

    /**
     * Inserts a new {@link viewModelElement} at its corresponding place in the {@link FileTreeView}
     *
     * @param viewModelElement
     */
    public void addViewModelElement(ViewModelElement viewModelElement) {
        FileTreeItem topLevelFolder = findTopLevelFolder(viewModelElement);
        FileTreeItem folder = findFolder(viewModelElement, topLevelFolder, 0);
        if (folder != null) {
            folder.getChildren().add(new FileTreeItem(createFile(viewModelElement), new ImageView(getImage(viewModelElement.getType
                    ())), viewModelElement));
            folder.getChildren().sort(Comparator.comparing(o -> o.getValue().toURI().toString()));
        } else {
            throw new RuntimeException("Destination folder for PlanElement " + viewModelElement.getName() + " does not exist!");
        }
    }

    public FileTreeItem removeViewModelElement(ViewModelElement viewModelElement) {
        FileTreeItem topLevelFolder = findTopLevelFolder(viewModelElement);
        FileTreeItem deletedItem = removeFromFolder(viewModelElement, topLevelFolder);
        return deletedItem;
    }

    public void updateDirectories(Path path) {
        if (path.toString().contains(plansPath)) {
            plansFileTreeItem.updateDirectories();
        } else if (path.toString().contains(rolesPath)) {
            rolesFileTreeItem.updateDirectories();
        } else if (path.toString().contains(taskPath)) {
            tasksFileTreeItem.updateDirectories();
        }
    }

    private FileTreeItem removeFromFolder(ViewModelElement modelElement, FileTreeItem treeItem) {
        for (TreeItem item : treeItem.getChildren()) {
            FileTreeItem itemToDelete = (FileTreeItem) item;
            if (!itemToDelete.getValue().isDirectory()) {
                if (((FileTreeItem) item).getViewModelElement().getId() == modelElement.getId()) {
                    treeItem.getChildren().remove(item);
                    treeItem.getChildren().sort(Comparator.comparing(o -> o.getValue().toURI().toString()));
                    return itemToDelete;
                }
            } else {
                return removeFromFolder(modelElement, itemToDelete);
            }
        }
        return null;
    }

    /**
     * Creates a file by combining to corresponding config path and the information given in a
     * {@link ViewModelElement} (relativeDirectory, name, ending)
     *
     * @param viewModelElement
     * @return
     */
    private File createFile(ViewModelElement viewModelElement) {
        switch (viewModelElement.getType()) {
            case Types.BEHAVIOUR:
                return Paths.get(plansPath, viewModelElement.getRelativeDirectory(), viewModelElement.getName() + ".beh").toFile();
            case Types.PLAN:
            case Types.MASTERPLAN:
                return Paths.get(plansPath, viewModelElement.getRelativeDirectory(), viewModelElement.getName() + ".pml").toFile();
            case Types.PLANTYPE:
                return Paths.get(plansPath, viewModelElement.getRelativeDirectory(), viewModelElement.getName() + ".pty").toFile();
            case Types.TASKREPOSITORY:
                return Paths.get(taskPath, viewModelElement.getRelativeDirectory(), viewModelElement.getName() + ".tsk").toFile();
            default:
                System.err.println("FileTreeView: " + viewModelElement.getType() + " not handled!");
                return null;
        }
    }

    /**
     * Finds the fitting top level folder to insert a {@link FileTreeItem}
     *
     * @param viewModelElement
     * @return
     */
    private FileTreeItem findTopLevelFolder(ViewModelElement viewModelElement) {
        switch (viewModelElement.getType()) {
            case Types.BEHAVIOUR:
            case Types.MASTERPLAN:
            case Types.PLAN:
            case Types.PLANTYPE:
                return plansFileTreeItem;
            case Types.TASKREPOSITORY:
                return tasksFileTreeItem;
            case Types.ROLE:
                return rolesFileTreeItem;
            default:
                System.err.println("FileTreeView: No top level folder for " + viewModelElement.getType() + " available!");
                return null;
        }
    }

    /**
     * Recursively looks for a fitting folder by using the relative path of a {@link ViewModelElement}
     * Returns the found {@link FileTreeItem} representing the folder, else null
     *
     * @param modelElement
     * @param treeItem
     * @param index
     * @return
     */
    private FileTreeItem findFolder(ViewModelElement modelElement, FileTreeItem treeItem, int index) {
        String relativePath = modelElement.getRelativeDirectory();
        String[] folders = relativePath.split(File.pathSeparator);
        if (folders.length == 1 && folders[0].isEmpty()) {
            return treeItem;
        }
        for (Object item : treeItem.getChildren()) {
            FileTreeItem newItem = (FileTreeItem) item;
            if (!(newItem.getValue().toString().endsWith(folders[index]))) {
                continue;
            }
            if (index == folders.length - 1) {
                return newItem;
            }
            return findFolder(modelElement, newItem, index + 1);
        }
        return null;
    }

    /**
     * Gets the fitting optical representation according to the given PlanElement elementType
     *
     * @param type
     * @return
     */

    private Image getImage(String type) {
        switch (type) {
            case Types.BEHAVIOUR:
                return new Image((getClass().getClassLoader().getResourceAsStream("images/behaviour24x24.png")));
            case Types.MASTERPLAN:
                return new Image((getClass().getClassLoader().getResourceAsStream("images/masterplan24x24.png")));
            case Types.PLAN:
                return new Image((getClass().getClassLoader().getResourceAsStream("images/plan24x24.png")));
            case Types.PLANTYPE:
                return new Image((getClass().getClassLoader().getResourceAsStream("images/plantype24x24.png")));
            case Types.TASKREPOSITORY:
                return new Image((getClass().getClassLoader().getResourceAsStream("images/tasks24x24.png")));
            case Types.FOLDER:
                return new Image((getClass().getClassLoader().getResourceAsStream("images/folder24x24.png")));
            default:
                System.err.println("FileTreeView: No image available for " + type + "!");
                return null;
        }
    }


    /**
     * Sets the PlansPath and adds a new {@link FileTreeItem} as top level folder
     * .pml, .pty and .beh files are added to this folder
     *
     * @param plansPath
     */
    public void setupPlansPath(String plansPath) {
        this.plansPath = plansPath;
        plansFileTreeItem = virtualDirectoryTreeItem.addTopLevelFolder(plansPath);
    }

    /**
     * Sets the TaskPath and adds a new {@link FileTreeItem} as top level folder
     * .tsk files are added to this folder
     *
     * @param taskPath
     */
    public void setupTaskPath(String taskPath) {
        this.taskPath = taskPath;
        tasksFileTreeItem = virtualDirectoryTreeItem.addTopLevelFolder(taskPath);
    }

    /**
     * Sets the PlansPath and adds a new {@link FileTreeItem} as top level folder
     * .cdefset, .rdefset, .graph and .rset files are added to this folder
     *
     * @param rolesPath
     */
    public void setupRolesPath(String rolesPath) {
        this.rolesPath = rolesPath;
        rolesFileTreeItem = virtualDirectoryTreeItem.addTopLevelFolder(rolesPath);
    }

    public GuiModificationEvent handleDelete() {
        //TODO: rework
        return null;
//        if (focusedProperty().get()) {
//            DeleteFileMenuItem deleteFileMenuItem = new DeleteFileMenuItem(getSelectionModel()
//                    .getSelectedItem()
//                    .getValue());
//            deleteFileMenuItem.deleteFile();
//            return null;
//        }
    }
}