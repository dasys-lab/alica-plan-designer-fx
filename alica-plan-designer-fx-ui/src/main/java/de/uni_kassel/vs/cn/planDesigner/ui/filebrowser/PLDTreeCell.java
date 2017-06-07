package de.uni_kassel.vs.cn.planDesigner.ui.filebrowser;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.ChangeAttributeValue;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.common.FileWrapper;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import java.io.File;
import java.io.IOException;

public class PLDTreeCell extends TreeCell<FileWrapper> {

    private TextField textField;

    private final CommandStack commandStack;


    public PLDTreeCell(CommandStack commandStack) {
        this.commandStack = commandStack;
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

            String name = newValue.unwrap().getName().substring(0, newValue.unwrap().getName().lastIndexOf("."));
            commandStack.storeAndExecute(new ChangeAttributeValue((PlanElement) objectToChange, "name",
                    String.class, name, (PlanElement)objectToChange));
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
}
