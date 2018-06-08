package de.uni_kassel.vs.cn.planDesigner.view.filebrowser;

import de.uni_kassel.vs.cn.planDesigner.common.FileWrapper;
import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryViewModel;
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
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;

public final class PLDFileTreeView extends TreeView<FileWrapper> {

    private MainWindowController controller;

    private boolean wasDragged;
    private TreeItem<FileWrapper> draggedItem;
    private Cursor originalCursor;
    private String startFolder;

    public PLDFileTreeView() {
        super(new VirtualDirectoryTreeItem());

        addEventHandler(MouseDragEvent.DRAG_DETECTED, e -> {
            System.out.println("Source: " + e.getSource() + " Target: " + e.getTarget());
            originalCursor = getCursor();
            Node node = ((Node) e.getTarget()).getParent();
            if (node instanceof PLDTreeCell == false) {
                e.consume();
                return;
            }
            draggedItem = ((PLDTreeCell) node).getTreeItem();
            startFolder = EMFModelUtils.createRelativeURI(draggedItem.getValue().unwrap()).toString();
            startFolder = startFolder.substring(0, startFolder.lastIndexOf(File.separator));
            String fileName = draggedItem.getValue().toString();
            if (fileName.endsWith(".beh")) {
                getScene().setCursor(new ImageCursor(new Image(PLDFileTreeView.class.getClassLoader()
                        .getResourceAsStream("images/behaviour24x24.png"))));
            } else if (fileName.endsWith(".pml")) {
                try {
                    Plan plan = (Plan) EMFModelUtils.loadAlicaFileFromDisk(draggedItem.getValue().unwrap());
                    if (plan.isMasterPlan()) {
                        getScene().setCursor(new ImageCursor(new Image(PLDFileTreeView.class.getClassLoader()
                                .getResourceAsStream("images/masterplan24x24.png"))));
                    } else {
                        getScene().setCursor(new ImageCursor(new Image(PLDFileTreeView.class.getClassLoader()
                                .getResourceAsStream("images/plan24x24.png"))));
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else if (fileName.endsWith(".pty")) {
                getScene().setCursor(new ImageCursor(new Image(PLDFileTreeView.class.getClassLoader()
                        .getResourceAsStream("images/plantype24x24.png"))));
            } else if (fileName.endsWith(".tsk")) {
                getScene().setCursor(new ImageCursor(new Image(PLDFileTreeView.class.getClassLoader()
                        .getResourceAsStream("images/tasks24x24.png"))));
            }
            wasDragged = true;
            e.consume();
        });

        addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            getScene().setCursor(originalCursor);
            RepositoryViewModel repositoryViewModel = RepositoryViewModel.getInstance();
            if (!wasDragged) {
                e.consume();
                return;
            }

            wasDragged = false;

            if (e.getPickResult() == null) {
                e.consume();
                return;
            }

            PLDTreeCell treeCell = null;
            if (e.getPickResult().getIntersectedNode().getParent() instanceof Group) {
                for (Node child : e.getPickResult().getIntersectedNode().getParent().getChildrenUnmodifiable()) {
                    if (child.getBoundsInParent().contains(e.getX(), e.getY(), e.getZ())) {
                        treeCell = (PLDTreeCell) child;
                        break;
                    }
                }
            } else {
                treeCell = (PLDTreeCell) e.getPickResult().getIntersectedNode().getParent();
            }

            File parent = treeCell.getTreeItem().getValue().unwrap();

            if (parent.isDirectory() == false) {
                parent = treeCell.getTreeItem().getParent().getValue().unwrap();
            }

            if (startFolder.equals(EMFModelUtils.createRelativeURI(parent).toString())) {
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

                Optional<Pair<Plan, Path>> first = repositoryViewModel
                        .getPlans()
                        .stream()
                        .filter(f -> f.getValue().toFile().equals(draggedItem.getValue().unwrap()))
                        .findFirst();

                EObject result = first.isPresent() ? first.get().getKey() : null;

                if (result != null) {
                    for (Pair<Plan, Path> pair : repositoryViewModel.getPlans()) {
                        if (pair.getKey() == (Plan) result) {
                            repositoryViewModel.getPlans().remove(pair);
                            break;
                        }
                    }

                } else {
                    Optional<Pair<Behaviour, Path>> second = repositoryViewModel
                            .getBehaviours()
                            .stream()
                            .filter(f -> f.getValue().toFile().equals(draggedItem.getValue().unwrap()))
                            .findFirst();

                    result = second.isPresent() ? second.get().getKey() : null;
                    if (result != null) {
                        for (Pair<Behaviour, Path> pair : repositoryViewModel.getBehaviours()) {
                            if (pair.getKey() == (Behaviour) result) {
                                repositoryViewModel.getBehaviours().remove(pair);
                                break;
                            }
                        }
                    }
                }

                if (result == null) {
                    Optional<Pair<PlanType, Path>> third = repositoryViewModel
                            .getPlanTypes()
                            .stream()
                            .filter(f -> f.getValue().toFile().equals(draggedItem.getValue().unwrap()))
                            .findFirst();

                    result = third.isPresent() ? third.get().getKey() : null;
                    if (result != null) {
                        for (Pair<PlanType, Path> pair : repositoryViewModel.getPlanTypes()) {
                            if (pair.getKey() == (PlanType) result) {
                                repositoryViewModel.getPlanTypes().remove(pair);
                                break;
                            }
                        }
                    }
                }

                //TODO tasks ?

                if (draggedItem.getValue().unwrap().toString().endsWith("pml")) {
                    EObject pmlExObject = EMFModelUtils.loadAlicaFileFromDisk(new File(draggedItem.getValue().unwrap().toString() + "ex"));
                    EMFModelUtils.moveAlicaFile(result, (PmlUiExtensionMap) pmlExObject,
                            new File(parent, draggedItem.getValue().unwrap().getName()));
                } else {
                    EMFModelUtils.moveAlicaFile(result, null,
                            new File(parent, draggedItem.getValue().unwrap().getName()));
                }
            } catch (IOException e1) {
                throw new RuntimeException(e1);
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

    public synchronized void updateTreeView(WatchEvent.Kind kind, Path child) {

        ((VirtualDirectoryTreeItem) getRoot()).updateDirectory(kind, child);
        if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
            try {
                RepositoryViewModel.getInstance().init();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            RepositoryTabPane repositoryTabPane = MainWindowController.getInstance().getRepositoryTabPane();
//            Tab previousTab = repositoryTabPane.getSelectionModel().getSelectedItem();
//            repositoryTabPane.init();
//            repositoryTabPane.getSelectionModel().select(previousTab);

        }
    }

    public void setController(MainWindowController controller) {
        this.controller = controller;
    }
}