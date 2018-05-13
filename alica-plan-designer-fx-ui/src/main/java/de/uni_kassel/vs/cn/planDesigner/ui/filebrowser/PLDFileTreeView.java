package de.uni_kassel.vs.cn.planDesigner.ui.filebrowser;

import de.uni_kassel.vs.cn.planDesigner.alica.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanType;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.Configuration;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.WorkspaceManager;
import de.uni_kassel.vs.cn.planDesigner.alica.util.RepoViewBackend;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.common.FileWrapper;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtensionMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import org.eclipse.emf.ecore.EObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.Optional;

/**
 * Created by marci on 16.10.16.
 */
public final class PLDFileTreeView extends TreeView<FileWrapper> {

    private MainController controller;

    private boolean wasDragged;
    private TreeItem<FileWrapper> draggedItem;

    public PLDFileTreeView() {
        super(new VirtualDirectoryTreeItem());


        addEventHandler(MouseDragEvent.DRAG_DETECTED, e-> {
            System.out.println("Source: " + e.getSource() + " Target: " +e.getTarget());
            if (((Node)e.getTarget()).getParent() instanceof PLDTreeCell) {
                draggedItem = ((PLDTreeCell) ((Node) e.getTarget()).getParent()).getTreeItem();
                wasDragged = true;
            }
            e.consume();
        });

        addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            if (wasDragged) {//
                wasDragged = false;
                if (e.getPickResult() != null) {
                    PLDTreeCell treeCell = (PLDTreeCell) e.getPickResult().getIntersectedNode().getParent();
                    // TODO auslagern
                    File parent = treeCell.getTreeItem().getValue().unwrap();

                    if (parent.isDirectory() == false) {
                        parent = treeCell.getTreeItem().getParent().getValue().unwrap();
                    }

                    try {
                        if (draggedItem.getValue().unwrap().getName().endsWith("pml")) {
                            Files.move(new File(draggedItem.getValue().unwrap().toString() + "ex").toPath(),
                                    new File(parent, draggedItem.getValue().unwrap().getName() + "ex").toPath());
                        }
                        Files.move(draggedItem.getValue().unwrap().toPath(),
                                new File(parent, draggedItem.getValue().unwrap().getName()).toPath());

                        EObject result = null;
                        Optional<Pair<Plan, Path>> first = RepoViewBackend
                                .getInstance()
                                .getPlans()
                                .stream()
                                .filter(f -> f.getValue().toFile().equals(draggedItem.getValue().unwrap()))
                                .findFirst();

                        if (result != null) {
                            RepoViewBackend
                                    .getInstance()
                                    .getPlanTypes().remove(result);
                        }

                        result = first.isPresent() ? first.get().getKey() : null;

                        if (result == null) {
                            Optional<Pair<Behaviour, Path>> second = RepoViewBackend
                                    .getInstance()
                                    .getBehaviours()
                                    .stream()
                                    .filter(f -> f.getValue().toFile().equals(draggedItem.getValue().unwrap()))
                                    .findFirst();
                            result = second.isPresent() ? second.get().getKey() : null;
                            if (result != null) {
                                RepoViewBackend
                                        .getInstance()
                                        .getBehaviours().remove(result);
                            }
                        }

                        if (result == null) {
                            Optional<Pair<PlanType, Path>> third = RepoViewBackend
                                    .getInstance()
                                    .getPlanTypes()
                                    .stream()
                                    .filter(f -> f.getValue().toFile().equals(draggedItem.getValue().unwrap()))
                                    .findFirst();
                            result = third.isPresent() ? third.get().getKey() : null;
                            if (result != null) {
                                RepoViewBackend
                                        .getInstance()
                                        .getPlanTypes().remove(result);
                            }
                        }

                        if (draggedItem.getValue().unwrap().toString().endsWith("pml")) {
                            EObject pmlExObject = EMFModelUtils.loadAlicaFileFromDisk(new File(draggedItem.getValue().unwrap().toString() + "ex"));
                            EMFModelUtils.moveAlicaFile(result, (PmlUiExtensionMap) pmlExObject,
                                    new File(parent, draggedItem.getValue().unwrap().getName()));
                        }
                    } catch (IOException e1) {
                        throw new RuntimeException(e1);
                    }
                }
            }
            e.consume();
        });


        this.setShowRoot(false);
        this.setContextMenu(new PLDFileTreeViewContextMenu());
        setEditable(true);
        setCellFactory(param -> {
                TreeCell<FileWrapper> fileWrapperTreeCell = new PLDTreeCell(controller);
                fileWrapperTreeCell.setContextMenu(new PLDFileTreeViewContextMenu());
                fileWrapperTreeCell.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (newValue) {
                            ((PLDFileTreeViewContextMenu)fileWrapperTreeCell.getContextMenu())
                                    .setCommandStack(controller.getCommandStack());
                            ((PLDFileTreeViewContextMenu)fileWrapperTreeCell.getContextMenu())
                                    .setHintFile(fileWrapperTreeCell.getTreeItem().getValue().unwrap());
                        ((PLDFileTreeViewContextMenu)fileWrapperTreeCell.getContextMenu())
                                .setTreeCell(fileWrapperTreeCell);
                        }
                    }
                });

                if(param.getEditingItem() != null) {
                    fileWrapperTreeCell.setText(param.getEditingItem().getValue().unwrap().getName());
                    fileWrapperTreeCell.setGraphic(param.getEditingItem().getGraphic());
                }
                return fileWrapperTreeCell;
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
    private static final Configuration configuration = new WorkspaceManager().getActiveWorkspace().getConfiguration();

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
