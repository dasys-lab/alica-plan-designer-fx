package de.uni_kassel.vs.cn.planDesigner.ui.filebrowser;

import de.uni_kassel.vs.cn.planDesigner.alica.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.util.AllAlicaFiles;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.common.FileWrapper;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.AbstractEditorTab;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Pair;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.common.util.URI;

import java.io.File;
import java.nio.file.Path;

public class PLDTreeCell extends TreeCell<FileWrapper> {

    private TextField textField;

    public PLDTreeCell() {
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
        super.commitEdit(newValue);
        File unwrappedFile = getTreeItem().getValue().unwrap();
        FilteredList<Tab> filtered = MainController.getInstance()
                .getEditorTabPane()
                .getTabs()
                .filtered(e -> ((AbstractEditorTab<?>) e).getFilePath()
                        .equals(unwrappedFile.toPath()));
        MainController
                .getInstance()
                .getEditorTabPane()
                .getTabs()
                .removeAll(filtered.toArray(new Tab[]{}));
        if (unwrappedFile.getName().endsWith(".pml")) {
            Pair<Plan, Path> planPathPair = AllAlicaFiles.getInstance()
                    .getPlans()
                    .stream()
                    .filter(e -> e.getKey()
                            .getName()
                            .equals(unwrappedFile
                                    .getName().substring(0, unwrappedFile.getName().lastIndexOf(".pml"))))
                    .findFirst().get();
            AllAlicaFiles.getInstance().getPlans().remove(planPathPair);

            Resource resourceToUpdate = EMFModelUtils.getAlicaResourceSet().getResources()
                    .stream()
                    .filter(e -> e.getContents().get(0).equals(planPathPair.getKey()))
                    .findFirst().get();
            String oldURI = resourceToUpdate.getURI().toString();
            resourceToUpdate.setURI(URI.createFileURI(oldURI.substring(0, oldURI.lastIndexOf("/") + 1) + newValue.unwrap().getName()));

            AllAlicaFiles.getInstance().getPlans().add(new Pair<>(planPathPair.getKey(), newValue.unwrap().toPath()));
        } else if (unwrappedFile.getName().endsWith(".beh")) {
            Pair<Behaviour, Path> behaviourPathPair = AllAlicaFiles.getInstance()
                    .getBehaviours()
                    .stream()
                    .filter(e -> e.getKey()
                            .getName()
                            .equals(unwrappedFile
                                    .getName().substring(0, unwrappedFile.getName().lastIndexOf(".beh"))))
                    .findFirst().get();
            AllAlicaFiles.getInstance().getPlans().remove(behaviourPathPair);

            Resource resourceToUpdate = EMFModelUtils.getAlicaResourceSet().getResources()
                    .stream()
                    .filter(e -> e.getContents().get(0).equals(behaviourPathPair.getKey()))
                    .findFirst().get();
            String oldURI = resourceToUpdate.getURI().toString();
            resourceToUpdate.setURI(URI.createFileURI(oldURI.substring(0, oldURI.lastIndexOf("/") + 1) + newValue.unwrap().getName()));

            AllAlicaFiles.getInstance().getBehaviours().add(new Pair<>(behaviourPathPair.getKey(), newValue.unwrap().toPath()));

        } else if (unwrappedFile.getName().endsWith(".pty")) {
            Pair<Behaviour, Path> behaviourPathPair = AllAlicaFiles.getInstance()
                    .getBehaviours()
                    .stream()
                    .filter(e -> e.getKey()
                            .getName()
                            .equals(unwrappedFile
                                    .getName().substring(0, unwrappedFile.getName().lastIndexOf(".beh"))))
                    .findFirst().get();
            AllAlicaFiles.getInstance().getPlans().remove(behaviourPathPair);

            Resource resourceToUpdate = EMFModelUtils.getAlicaResourceSet().getResources()
                    .stream()
                    .filter(e -> e.getContents().get(0).equals(behaviourPathPair.getKey()))
                    .findFirst().get();
            String oldURI = resourceToUpdate.getURI().toString();
            resourceToUpdate.setURI(URI.createFileURI(oldURI.substring(0, oldURI.lastIndexOf("/") + 1) + newValue.unwrap().getName()));

            AllAlicaFiles.getInstance().getBehaviours().add(new Pair<>(behaviourPathPair.getKey(), newValue.unwrap().toPath()));
        }
        unwrappedFile.renameTo(newValue.unwrap());

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
