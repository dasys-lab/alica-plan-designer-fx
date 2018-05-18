package de.uni_kassel.vs.cn.planDesigner.ui.filebrowser;

import de.uni_kassel.vs.cn.planDesigner.command.change.ChangeAttributeValue;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.util.AlicaModelUtils;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.common.FileWrapper;
import de.uni_kassel.vs.cn.planDesigner.controller.ErrorWindowController;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import java.io.File;
import java.io.IOException;

public class PLDTreeCell extends TreeCell<FileWrapper> {

    private static final Logger LOG = LogManager.getLogger(PLDTreeCell.class);

    private TextField textField;

    private final MainController controller;


    public PLDTreeCell(MainController controller) {
        this.controller = controller;
        if (getItem() != null) {
            addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleDoubleClick);
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();

        if (textField == null) {
            createTextField();
        }
        setText(getItem().unwrap().getName());
        setGraphic(textField);
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem().unwrap().getName());
        setGraphic(getTreeItem().getGraphic());
    }

    @Override
    public void commitEdit(FileWrapper newValue) {
        if (! isEditing()) return;
            String name = newValue.unwrap().getName().substring(0, newValue.unwrap().getName().lastIndexOf("."));
        if (AlicaModelUtils.containsIllegalCharacter(name)) {
            final TreeItem<FileWrapper> treeItem = getTreeItem();
            final TreeView<FileWrapper> tree = getTreeView();
            if (tree != null) {
                // Inform the TreeView of the edit being ready to be committed.
                tree.fireEvent(new TreeView.EditEvent<>(tree,
                        TreeView.<FileWrapper>editCommitEvent(),
                        treeItem,
                        getItem(),
                        treeItem.getValue()));
            }
            LOG.warn("User tried to set illegal name: " + newValue.unwrap().getName());
            ErrorWindowController.createErrorWindow("This name is not allowed! These characters are forbidden: "
                    + AlicaModelUtils.forbiddenCharacters, null);

            return;
        }
        boolean isPlanElement = false;
        File unwrappedFile = getTreeItem().getValue().unwrap();
        EObject objectToChange = null;
        if (unwrappedFile.getName().endsWith(".pml") ||
                unwrappedFile.getName().endsWith(".pty") || unwrappedFile.getName().endsWith("beh")) {
            Resource resource = EMFModelUtils
                    .getAlicaResourceSet()
                    .getResources()
                    .stream()
                    .filter(e -> e.getURI().toFileString().contains(unwrappedFile.getName()))
                    .filter(e -> e.getURI().toFileString().contains("pmlex") == false)
                    .findFirst()
                    .get();
            objectToChange = resource.getContents().get(0);

            controller.getCommandStack()
                    .storeAndExecute(new ChangeAttributeValue((PlanElement) objectToChange, "name", name, (PlanElement)objectToChange));
            isPlanElement = true;
        }

        unwrappedFile.renameTo(newValue.unwrap());

        if (isPlanElement && objectToChange != null) {
            try {
                EMFModelUtils.saveAlicaFile(objectToChange);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        final TreeItem<FileWrapper> treeItem = getTreeItem();
        final TreeView<FileWrapper> tree = getTreeView();
        if (tree != null) {
            // Inform the TreeView of the edit being ready to be committed.
            tree.fireEvent(new TreeView.EditEvent<>(tree,
                    TreeView.<FileWrapper>editCommitEvent(),
                    treeItem,
                    getItem(),
                    newValue));
        }

    }

    @Override
    public void updateItem(FileWrapper item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (getItem() != null) {
                addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleDoubleClick);
            }
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(getTreeItem().getGraphic());
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    String absolutePath = getTreeItem().getValue().unwrap().getAbsolutePath();
                    FileWrapper fileWrapper = null;
                    if (absolutePath != null) {
                        fileWrapper = new FileWrapper(new File(absolutePath.replace(getItem().unwrap().getName(), textField.getText())));
                    }
                    commitEdit(fileWrapper);
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }

    private void handleDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            if (getItem().unwrap().isDirectory()) {
                getTreeItem().setExpanded(getTreeItem().isExpanded() == false);
            } else {
                controller.openFile(getItem().unwrap());
            }
        }
    }
}
