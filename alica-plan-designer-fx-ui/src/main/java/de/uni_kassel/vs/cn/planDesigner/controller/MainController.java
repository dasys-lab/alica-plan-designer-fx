package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.generator.Codegenerator;
import de.uni_kassel.vs.cn.planDesigner.PlanDesigner;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.AbstractEditorTab;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.EditorTabPane;
import de.uni_kassel.vs.cn.planDesigner.ui.filebrowser.PLDFileTreeView;
import de.uni_kassel.vs.cn.planDesigner.ui.menu.EditMenu;
import de.uni_kassel.vs.cn.planDesigner.ui.menu.NewResourceMenu;
import de.uni_kassel.vs.cn.planDesigner.ui.properties.PropertyTabPane;
import de.uni_kassel.vs.cn.planDesigner.ui.repo.RepositoryTabPane;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by marci on 16.10.16.
 */
public class MainController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(MainController.class);

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
        MenuItem generateCurrentFile = new MenuItem(I18NRepo.getString("label.menu.generation.file"));
        generateCurrentFile.setDisable(true);
        getEditorTabPane().getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                PlanElement editable = ((AbstractEditorTab) newValue).getEditable();
                if (editable instanceof AbstractPlan) {
                    generateCurrentFile.setDisable(false);
                }
            } else {
                generateCurrentFile.setDisable(true);
            }
        });

        generateCurrentFile.setOnAction(e -> {
            PlanElement planElement = ((AbstractEditorTab) getEditorTabPane()
                    .getSelectionModel().getSelectedItem()).getEditable();
            try {
            	waitOnProgressWindow(() -> new Codegenerator().generate((AbstractPlan)planElement));
            } catch (RuntimeException ex) {
                LOG.error("error while generating code", ex);
                ErrorWindowController.createErrorWindow(I18NRepo.getString("label.error.codegen"), null);
            }
        });
        regenerateItem.setOnAction(e -> {
            try {
            	waitOnProgressWindow(() -> new Codegenerator().generate());
                
            } catch (RuntimeException ex) {
                LOG.error("error while generating code", ex);
                ErrorWindowController.createErrorWindow(I18NRepo.getString("label.error.codegen"), null);
            }

        });
        codegenerationMenu.getItems().addAll(generateCurrentFile, regenerateItem);
        menus.add(codegenerationMenu);

        return menus;
    }

	private void waitOnProgressWindow(Runnable toWaitOn) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("errorWindow.fxml"));
		try {
		    Parent rootOfDialog = fxmlLoader.load();
		    ErrorWindowController controller = fxmlLoader.getController();
		    controller.getConfirmButton().setVisible(false);
		    Stage stage = new Stage();
		    stage.setResizable(false);
		    stage.setTitle(I18NRepo.getString("label.generate.sources"));
		    stage.setScene(new Scene(rootOfDialog));
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner(PlanDesigner.getPrimaryStage());
		    stage.showAndWait();
		    new Thread(() -> {
		    	toWaitOn.run();
		    	stage.close();
		    }).start();
		    int[] counter = {0};
		    new Thread(() -> {
		    	Platform.runLater(() -> controller.setErrorLabelText(I18NRepo.getString("label.generation.progress" + counter[0]++%3+1)));
		    	try {
					Thread.sleep(1000L);
				} catch (InterruptedException ignored) {
				}
		    }).start();

		} catch (IOException er) {
		    // if the helper window is not loadable something is really wrong here
		    er.printStackTrace();
		    System.exit(1);
		}
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
