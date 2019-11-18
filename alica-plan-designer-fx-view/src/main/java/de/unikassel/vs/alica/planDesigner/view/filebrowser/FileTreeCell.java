package de.unikassel.vs.alica.planDesigner.view.filebrowser;

import de.unikassel.vs.alica.planDesigner.controller.ErrorWindowController;
import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiChangeAttributeEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.model.SerializableViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class FileTreeCell extends TreeCell<File> {

    private static final Logger LOG = LogManager.getLogger(FileTreeCell.class);

    private TextField textField;

    private final MainWindowController controller;


    public FileTreeCell(MainWindowController controller) {
        this.controller = controller;
    }

    @Override
    public void startEdit() {
        super.startEdit();

        if (textField == null) {
            createTextField();
        }
        setText(getItem().getName());
        setGraphic(textField);
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        Platform.runLater(() -> {
            super.cancelEdit();
            setText(getItem().getName());
            setGraphic(getTreeItem().getGraphic());
        });
    }

    @Override
    public void commitEdit(File newValue) {
        if (!isEditing()) return;

        int fileEndingPosition = newValue.getName().lastIndexOf(".");
        //If the rename file is a folder
        for (Object object : getTreeView().getRoot().getChildren())
        {
            TreeItem treeItem = (TreeItem) object;
            if(treeItem.getValue().toString().equals(getTreeItem().getValue().toString())){
                Dialog dialog = new Dialog();
                dialog.setTitle("Folder rename fail");
                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.setStyle("-fx-background-color: #fff;");
                dialogPane.setContentText("Cannot rename the root folder!!!");
                ButtonType okButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().add(okButtonType);
                Button okButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);
                okButton.setAlignment(Pos.CENTER);
                dialog.showAndWait();
                return;
            }
        }
        if (fileEndingPosition < 0) {
            GuiChangeAttributeEvent guiChangeAttributeEvent = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, Types.FOLDER, newValue.getName());
            guiChangeAttributeEvent.setAttributeName(this.getTreeItem().getValue().toString());
            guiChangeAttributeEvent.setNewValue(newValue);
            controller.getGuiModificationHandler().handle(guiChangeAttributeEvent);
        } else {
            ViewModelElement element = ((FileTreeItem) getTreeItem()).getViewModelElement();
            GuiChangeAttributeEvent guiChangeAttributeEvent = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, element.getType(), element.getName());

            guiChangeAttributeEvent.setElementId(element.getId());
            guiChangeAttributeEvent.setParentId(element.getId());
            guiChangeAttributeEvent.setAttributeType(String.class.getSimpleName());
            guiChangeAttributeEvent.setAttributeName("name");
            guiChangeAttributeEvent.setNewValue(newValue.getName().substring(0, fileEndingPosition));
            controller.getGuiModificationHandler().handle(guiChangeAttributeEvent);
        }

        final TreeItem<File> treeItem = getTreeItem();
        final TreeView<File> tree = getTreeView();
        if (tree != null) {
            // Inform the TreeView of the edit being ready to be committed.
            tree.fireEvent(new TreeView.EditEvent<>(tree,
                    TreeView.<File>editCommitEvent(),
                    treeItem,
                    getItem(),
                    newValue));
        }

    }

    private boolean checkForCorrectFileEnding(File newValue, String ending) {
        if (!newValue.getName().endsWith(ending)) {
            getTreeView()
                    .fireEvent(new TreeView.EditEvent<>(getTreeView(),
                            TreeView.editCancelEvent(), getTreeItem(), getItem(), getItem()));
            ErrorWindowController.createErrorWindow(
                    I18NRepo.getInstance().getString("label.error.rename.illegalEnding"), null);
            return true;
        }
        return false;
    }

    @Override
    public void updateItem(File item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
            return;
        }

        if (getItem() != null) {
            setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() == 2) {
                        if (getItem().isDirectory()) {
                            getTreeItem().setExpanded(!getTreeItem().isExpanded());
                        } else {
                            controller.openFile((SerializableViewModel) ((FileTreeItem) getTreeItem()).getViewModelElement());
                        }
                    }
                }
            });
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

    private void createTextField() {
        textField = new TextField(getString());
        textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    String absolutePath = getTreeItem().getValue().getAbsolutePath();
                    File fileWrapper = null;
                    if (absolutePath != null) {
                        fileWrapper = new File(absolutePath.replace(getItem().getName(), textField.getText()));
                    }
                    commitEdit(fileWrapper);
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().getName();
    }
}
