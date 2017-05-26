package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.generator.Codegenerator;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.AbstractEditorTab;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.EditorTabPane;
import de.uni_kassel.vs.cn.planDesigner.ui.filebrowser.PLDFileTreeView;
import de.uni_kassel.vs.cn.planDesigner.ui.menu.EditMenu;
import de.uni_kassel.vs.cn.planDesigner.ui.menu.NewResourceMenu;
import de.uni_kassel.vs.cn.planDesigner.ui.properties.PropertyTabPane;
import de.uni_kassel.vs.cn.planDesigner.ui.repo.RepositoryTabPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by marci on 16.10.16.
 */
public class MainController implements Initializable {

    private static MainController MAIN_CONTROLLER;

    @FXML
    private PLDFileTreeView fileTreeView;

    @FXML
    private PropertyTabPane propertyAndStatusTabPane;

    @FXML
    private RepositoryTabPane repositoryTabPane;

    @FXML
    private EditorTabPane editorTabPane;

    @FXML
    private MenuBar menuBar;

    private CommandStack commandStack = new CommandStack();

    public MainController() {
        super();
        MAIN_CONTROLLER = this;
    }

    public static MainController getInstance() {
        return MAIN_CONTROLLER;
    }

    public void initialize(URL location, ResourceBundle resources) {
        fileTreeView.setController(this);
        editorTabPane.setCommandStack(commandStack);
        editorTabPane.getTabs().clear();
        repositoryTabPane.init();
        menuBar.getMenus().addAll(createMenus());
        propertyAndStatusTabPane.init(editorTabPane);
    }

    /**
     *
     * @return
     */
    private List<Menu> createMenus() {
        // TODO this desperately needs refactoring
        List<Menu> menus = new ArrayList<>();
        Menu fileMenu = new Menu(I18NRepo.getString("label.menu.file"));
        fileMenu.getItems().add(new NewResourceMenu());
        MenuItem saveItem = new MenuItem(I18NRepo.getString("label.menu.file.save"));
        saveItem.setOnAction(event -> ((AbstractEditorTab<?>) editorTabPane.getSelectionModel().getSelectedItem()).save());
        saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        fileMenu.getItems().add(saveItem);
        menus.add(fileMenu);
        menus.add(new EditMenu(commandStack, editorTabPane));
        Menu codegenerationMenu = new Menu(I18NRepo.getString("label.menu.generation"));
        MenuItem regenerateItem = new MenuItem(I18NRepo.getString("label.menu.generation.regenerate"));
        regenerateItem.setOnAction(e -> new Codegenerator().generate());
        codegenerationMenu.getItems().add(regenerateItem);
        menus.add(codegenerationMenu);

        return menus;
    }

    /**
     * delegate to {@link EditorTabPane#openTab(java.nio.file.Path)}
     *
     * @param toOpen
     */
    public void openFile(File toOpen) {
        editorTabPane.openTab(toOpen.toPath());
    }

    public CommandStack getCommandStack() {
        return commandStack;
    }

    public EditorTabPane getEditorTabPane() {
        return editorTabPane;
    }

    public RepositoryTabPane getRepositoryTabPane() {
        return repositoryTabPane;
    }

    public PLDFileTreeView getFileTreeView() {
        return fileTreeView;
    }
}
