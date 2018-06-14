package de.uni_kassel.vs.cn.planDesigner.view.filebrowser;

import de.uni_kassel.vs.cn.planDesigner.common.FileWrapper;
import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
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

import javax.swing.event.TreeSelectionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;

public final class FileTreeView extends TreeView<FileWrapper> {

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
    private I18NRepo i18NRepo;
    private final String planString;
    private final String planTypeString;
    private final String masterPlanString;
    private final String behaviourString;
    private final String taskRepositoryString;

    public FileTreeView() {
        super(new VirtualDirectoryTreeItem());
        virtualDirectoryTreeItem = (VirtualDirectoryTreeItem) getRoot();

        i18NRepo = I18NRepo.getInstance();
        behaviourString = i18NRepo.getString("alicatype.behaviour");
        planString = i18NRepo.getString("alicatype.plan");
        planTypeString = i18NRepo.getString("alicatype.plantype");
        masterPlanString = i18NRepo.getString("alicatype.masterplan");
        taskRepositoryString = i18NRepo.getString("alicatype.taskrepository");

        // Setup Drag support
        addEventHandler(MouseDragEvent.DRAG_DETECTED, e -> {
            originalCursor = getCursor();
            Node node = ((Node) e.getTarget()).getParent();
            if (node instanceof FileTreeCell == false) {
                System.out.println("event consumed");
                e.consume();
                return;
            }
            System.out.println("Source: " + e.getSource() + " Target: " + e.getTarget());
            draggedItem = (FileTreeItem) ((FileTreeCell) node).getTreeItem();
            startFolder = draggedItem.getValue().unwrap().toString();
            startFolder = startFolder.substring(0, startFolder.lastIndexOf(File.separator));
            String type = draggedItem.getTreeViewModelElement().getType();
            if (type.equals(behaviourString)) {
                getScene().setCursor(new ImageCursor(new Image(FileTreeView.class.getClassLoader()
                        .getResourceAsStream("images/behaviour24x24.png"))));
            } else if (type.equals(planString)) {
                getScene().setCursor(new ImageCursor(new Image(FileTreeView.class.getClassLoader()
                        .getResourceAsStream("images/plan24x24.png"))));
            } else if (type.equals(masterPlanString)) {
                getScene().setCursor(new ImageCursor(new Image(FileTreeView.class.getClassLoader()
                        .getResourceAsStream("images/masterplan24x24.png"))));
            } else if (type.equals(planTypeString)) {
                getScene().setCursor(new ImageCursor(new Image(FileTreeView.class.getClassLoader()
                        .getResourceAsStream("images/plantype24x24.png"))));
            } else if (type.equals(taskRepositoryString)) {
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

            //TODO fire file moved event
//            try {
//                if (draggedItem.getValue().unwrap().getName().endsWith("pml")) {
//                    Files.move(new File(draggedItem.getValue().unwrap().toString() + "ex").toPath(),
//                            new File(parent, draggedItem.getValue().unwrap().getName() + "ex").toPath());
//                }
//                Files.move(draggedItem.getValue().unwrap().toPath(),
//                        new File(parent, draggedItem.getValue().unwrap().getName()).toPath());
//            } catch (IOException e1) {
//                throw new RuntimeException(e1);
//            }
            e.consume();
        });


        this.setShowRoot(false);
        this.setContextMenu(new FileTreeViewContextMenu());
        this.setEditable(true);

        setCellFactory(param -> {
            TreeCell<FileWrapper> fileWrapperTreeCell = new FileTreeCell(controller);
            fileWrapperTreeCell.setContextMenu(new FileTreeViewContextMenu());
            fileWrapperTreeCell.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (newValue) {
                        ((FileTreeViewContextMenu) fileWrapperTreeCell.getContextMenu())
                                .setHintFile(fileWrapperTreeCell.getTreeItem().getValue().unwrap());
                        ((FileTreeViewContextMenu) fileWrapperTreeCell.getContextMenu())
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
     * Inserts a new {@link TreeViewModelElement} at its corresponding place in the {@link FileTreeView}
     *
     * @param treeViewModelElement
     */
    public void addTreeViewModelElement(TreeViewModelElement treeViewModelElement) {
        FileTreeItem topLevelFolder = findTopLevelFolder(treeViewModelElement);
        FileTreeItem folder = findFolder(treeViewModelElement, topLevelFolder, 0);
        if (folder != null) {
            folder.getChildren().add(new FileTreeItem(new FileWrapper(createFile(treeViewModelElement)), new ImageView(getImage(treeViewModelElement.getType
                    ())), treeViewModelElement));
            folder.getChildren().sort(Comparator.comparing(o -> o.getValue().unwrap().toURI().toString()));
        } else {
            throw new RuntimeException("Destination folder for PlanElement " + treeViewModelElement.getName() + " does not exist!");
        }
    }

    /**
     * Creates a file by combining to corresponding config path and the information given in a
     * {@link TreeViewModelElement} (relativeDirectory, name, ending)
     *
     * @param treeViewModelElement
     * @return
     */
    private File createFile(TreeViewModelElement treeViewModelElement) {
        File file;
        if (treeViewModelElement.getType().equals(behaviourString)) {
            file = Paths.get(plansPath, treeViewModelElement.getRelativeDirectory(), treeViewModelElement.getName() + ".beh").toFile();
        } else if (treeViewModelElement.getType().equals(masterPlanString) || treeViewModelElement.getType().equals(planString)) {
            file = Paths.get(plansPath, treeViewModelElement.getRelativeDirectory(), treeViewModelElement.getName() + ".pml").toFile();
        } else if (treeViewModelElement.getType().equals(planTypeString)) {
            file = Paths.get(plansPath, treeViewModelElement.getRelativeDirectory(), treeViewModelElement.getName() + ".pty").toFile();
        } else if (treeViewModelElement.getType().equals(taskRepositoryString)) {
            file = Paths.get(taskPath, treeViewModelElement.getRelativeDirectory(), treeViewModelElement.getName() + ".tsk").toFile();
        } else {
            throw new RuntimeException("Trying to create file for unknown type " + treeViewModelElement.getType() + "!");
        }
        return file;
    }

    /**
     * Finds the fitting top level folder to insert a {@link FileTreeItem}
     *
     * @param treeViewModelElement
     * @return
     */
    private FileTreeItem findTopLevelFolder(TreeViewModelElement treeViewModelElement) {
        if (treeViewModelElement.getType().equals(planTypeString) || treeViewModelElement.getType().equals(planString) || treeViewModelElement.getType()
                .equals(masterPlanString)) {
            return plansFileTreeItem;
        } else if (treeViewModelElement.getType().equals(taskRepositoryString)) {
            return tasksFileTreeItem;
        } else {
            return rolesFileTreeItem;
        }
    }

    /**
     * Recursively looks for a fitting folder by using the relative path of a {@link TreeViewModelElement}
     * Returns the found {@link FileTreeItem} representing to folder, else null
     *
     * @param modelElement
     * @param treeItem
     * @param index
     * @return
     */
    private FileTreeItem findFolder(TreeViewModelElement modelElement, FileTreeItem treeItem, int index) {
        String relativePath = modelElement.getRelativeDirectory();
        String[] folders = relativePath.split(File.pathSeparator);
        if (folders.length == 1 && folders[0].isEmpty()) {
            return treeItem;
        }
        System.out.println(treeItem.toString());
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
     * Gets the fitting optical representation according to the given PlanElement type
     *
     * @param type
     * @return
     */

    private Image getImage(String type) {
        Image listItemImage;
        if (type.equals(behaviourString)) {
            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/behaviour24x24.png")));
        } else if (type.equals(masterPlanString)) {
            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/masterplan24x24.png")));
        } else if (type.equals(planString)) {
            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/plan24x24.png")));
        } else if (type.equals(planTypeString)) {
            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/plantype24x24.png")));
        } else if (type.equals(taskRepositoryString)) {
            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/tasks24x24.png")));
        } else if (type.isEmpty()) {
            listItemImage = new Image((getClass().getClassLoader().getResourceAsStream("images/folder24x24.png")));
        } else {
            return null;
        }
        return listItemImage;
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
}