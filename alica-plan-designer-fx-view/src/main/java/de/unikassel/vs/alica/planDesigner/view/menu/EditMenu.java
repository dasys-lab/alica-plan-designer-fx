package de.unikassel.vs.alica.planDesigner.view.menu;

import de.unikassel.vs.alica.planDesigner.PlanDesignerApplication;
import de.unikassel.vs.alica.planDesigner.controller.AlicaConfWindowController;
import de.unikassel.vs.alica.planDesigner.controller.ConfigurationWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTabPane;
import de.unikassel.vs.alica.planDesigner.view.filebrowser.FileTreeView;
import de.unikassel.vs.alica.planDesigner.view.repo.RepositoryTabPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;

public class EditMenu extends Menu {

    private DeleteElementMenuItem deleteMenuItem;
    private MenuItem undoItem;
    private MenuItem redoItem;
    private MenuItem alicaConfItem;
    private final MenuItem configItem;
    private I18NRepo i18NRepo;
    private Stage configStage;
    private Stage alicaConfStage;

    private FileTreeView fileTreeView;
    private EditorTabPane editorTabPane;
    private RepositoryTabPane repositoryTabPane;
    private ConfigurationWindowController configWindowController;
    private IGuiModificationHandler guiModificationHandler;
    private AlicaConfWindowController alicaConfWindowController;

    public EditMenu(FileTreeView fileTreeView, EditorTabPane editorTabPane, RepositoryTabPane repositoryTabPane,
                    ConfigurationWindowController configWindowController, AlicaConfWindowController alicaConfWindowController) {
        super(I18NRepo.getInstance().getString("label.menu.edit"));
        this.fileTreeView = fileTreeView;
        this.editorTabPane = editorTabPane;
        this.repositoryTabPane = repositoryTabPane;
        this.configWindowController = configWindowController;
        this.alicaConfWindowController = alicaConfWindowController;

        i18NRepo = I18NRepo.getInstance();

        deleteMenuItem = new DeleteElementMenuItem(null, guiModificationHandler);
        deleteMenuItem.setOnAction(event -> delete());

        undoItem = new MenuItem(i18NRepo.getString("label.menu.edit.undo"));
        undoItem.setDisable(true);
        undoItem.setOnAction(event -> undo());
        undoItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));

        redoItem = new MenuItem(i18NRepo.getString("label.menu.edit.redo"));
        redoItem.setDisable(true);
        redoItem.setOnAction(event -> redo());
        redoItem.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));

        configItem = new MenuItem(i18NRepo.getString("label.menu.edit.config"));
        configItem.setOnAction(event -> openConfigMenu());

        alicaConfItem = new MenuItem("Alica Configuration");
        alicaConfItem.setOnAction(event -> openAlicaConfMenu());

        getItems().addAll(undoItem, redoItem, deleteMenuItem, configItem, alicaConfItem);
    }

    // TODO: call this methods from the controller - reacting to changes in the commandstack
    public void setUndoDisabled(boolean value) {
        undoItem.setDisable(value);
    }

    // TODO: call this methods from the controller - reacting to changes in the commandstack
    public void setRedoItemDisabled(boolean value) {
        redoItem.setDisable(value);
    }

    // TODO: call this methods from the controller - reacting to changes in the commandstack and selected treeviewitem
    public void setDeleteItemDisabled(boolean value) {
        deleteMenuItem.setDisable(value);
    }

    public void setGuiModificationHandler(IGuiModificationHandler handler) {
        this.guiModificationHandler = handler;
    }

    private void undo() {
        guiModificationHandler.handleUndo();
    }

    private void redo() {
        guiModificationHandler.handleRedo();
    }

    private void openAlicaConfMenu() {
        if (alicaConfStage == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("alicaConfWindow.fxml"));
            fxmlLoader.setController(this.alicaConfWindowController);
            Parent window;
            try {
                window = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            alicaConfStage = new Stage();
            alicaConfStage.setResizable(false);
            alicaConfStage.setTitle(i18NRepo.getString("label.alicaConf.title"));
            alicaConfStage.setScene(new Scene(window));
            alicaConfStage.initOwner(PlanDesignerApplication.getPrimaryStage());
        }
        alicaConfStage.show();
        alicaConfStage.toFront();
    }
    private void openConfigMenu() {
        if (configStage == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("configurationWindow.fxml"));
            fxmlLoader.setController(this.configWindowController);
            Parent window;
            try {
                window = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            configStage = new Stage();
            configStage.setResizable(false);
            configStage.setTitle(i18NRepo.getString("label.config.title"));
            configStage.setScene(new Scene(window));
            configStage.initOwner(PlanDesignerApplication.getPrimaryStage());
        }
        configStage.show();
        configStage.toFront();
    }

    /**
     * Overwrites the deleteMenuItem's onRemoveElement method.
     */
    private void delete() {
        GuiModificationEvent event;

        event = editorTabPane.handleDelete();
        if (event == null) {
            event = repositoryTabPane.handleDelete();
        }

        if (event == null) {
            event = fileTreeView.handleDelete();
        }

        if (event != null) {
            guiModificationHandler.handle(event);
        }
    }
}
