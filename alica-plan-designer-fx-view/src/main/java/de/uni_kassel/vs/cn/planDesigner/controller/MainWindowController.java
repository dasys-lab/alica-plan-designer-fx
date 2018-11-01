package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiStatusHandler;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.AbstractPlanElementContainer;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.AbstractPlanTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.EditorTabPane;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.IEditorTab;
import de.uni_kassel.vs.cn.planDesigner.view.filebrowser.FileTreeView;
import de.uni_kassel.vs.cn.planDesigner.view.img.AlicaIcon;
import de.uni_kassel.vs.cn.planDesigner.view.img.AlicaIcon.Size;
import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.view.menu.EditMenu;
import de.uni_kassel.vs.cn.planDesigner.view.menu.NewResourceMenu;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryTabPane;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

//--------------------------------------------------------------------------------------------
//  SINGLETON INSTANCE
//--------------------------------------------------------------------------------------------
    private static volatile MainWindowController instance;

    public static MainWindowController getInstance() {
        if (instance == null) {
            synchronized (MainWindowController.class) {
                if (instance == null) {
                    instance = new MainWindowController();
                }
            }
        }

        return instance;
    }

    private MainWindowController() {
        this.i18NRepo = I18NRepo.getInstance();
    }

//--------------------------------------------------------------------------------------------
//  CONSTANTS AND STATICS
//--------------------------------------------------------------------------------------------
    private static final Logger LOG = LogManager.getLogger(MainWindowController.class);
    public static Cursor FORBIDDEN_CURSOR = new ImageCursor(new AlicaIcon("forbidden", Size.NONE));

//--------------------------------------------------------------------------------------------
//  FXML INJECTED
//--------------------------------------------------------------------------------------------
    @FXML
    private FileTreeView fileTreeView;

    //@FXML
    //private PropertyTabPane propertyAndStatusTabPane;

    @FXML
    private RepositoryTabPane repositoryTabPane;

    @FXML
    private EditorTabPane editorTabPane;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Rectangle statusBlob;

    @FXML
    private Text statusText;

//--------------------------------------------------------------------------------------------
//  FIELDS
//--------------------------------------------------------------------------------------------
    // ---- GUI STUFF ----
    private I18NRepo i18NRepo;
    private Menu fileMenu;
    private Menu codeGenerationMenu;
    private EditMenu editMenu;

    // ---- MODEL STUFF ----
    private String plansPath;
    private String tasksPath;
    private String rolesPath;

    // ---- HANDLE & CONTROLLER ----
    private ConfigurationWindowController configWindowController;
    private IGuiStatusHandler guiStatusHandler;
    private IGuiModificationHandler guiModificationHandler;

//--------------------------------------------------------------------------------------------
//  GETTER & SETTER
//--------------------------------------------------------------------------------------------
    // ---- GETTER ----
    public String getPlansPath() {
    return plansPath;
}
    public String getTasksPath() {
        return tasksPath;
    }
    public String getRolesPath() {
        return rolesPath;
    }

    public FileTreeView getFileTreeView() {
        return fileTreeView;
    }
    public IGuiModificationHandler getGuiModificationHandler() {
        return guiModificationHandler;
    }
    public EditorTabPane getEditorTabPane() {
        return editorTabPane;
    }
    public RepositoryTabPane getRepositoryTabPane() {
        return repositoryTabPane;
    }

    // ---- SETTER ----
    public void setConfigWindowController(ConfigurationWindowController configWindowController) {
        this.configWindowController = configWindowController;
    }
    public void setGuiStatusHandler(IGuiStatusHandler guiStatusHandler) {
        this.guiStatusHandler = guiStatusHandler;
    }
    public void setGuiModificationHandler(IGuiModificationHandler creationHandler) {
        this.guiModificationHandler = creationHandler;
    }

//--------------------------------------------------------------------------------------------
//  INTERFACE IMPLEMENTATIONS
//--------------------------------------------------------------------------------------------
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (configWindowController == null) {
            throw new RuntimeException("The member configWindowController need to be set through the public setter, before calling initialize()!");
        }

        // clear
        repositoryTabPane.getTabs().clear();
        editorTabPane.getTabs().clear();

        fileTreeView.setController(this);
        repositoryTabPane.setGuiModificationHandler(guiModificationHandler);
        editorTabPane.setGuiModificationHandler(guiModificationHandler);

        // propertyAndStatusTabPane.init(editorTabPane);

        statusText.setVisible(false);
        menuBar.getMenus().addAll(createMenus());
        guiStatusHandler.handleGuiInitialized();
    }

//--------------------------------------------------------------------------------------------
//  SETUP
//--------------------------------------------------------------------------------------------
    private List<Menu> createMenus() {
        List<Menu> menus = new ArrayList<>();

        // ---- FILE MENU ----
        fileMenu = new Menu(i18NRepo.getString("label.menu.file"));
        fileMenu.getItems().add(new NewResourceMenu(null));

        // -- SAVE MENU --
        MenuItem saveItem = new MenuItem(i18NRepo.getString("label.menu.file.save"));
        saveItem.setOnAction(event -> {
            if (editorTabPane.getSelectionModel().getSelectedItem() == null) {
                return;
            }
            ((IEditorTab) editorTabPane.getSelectionModel().getSelectedItem()).save();
        });
        saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        fileMenu.getItems().add(saveItem);
        fileMenu.setDisable(true);
        menus.add(fileMenu);

        // ---- EDIT MENU ----
        editMenu = new EditMenu(fileTreeView, editorTabPane, repositoryTabPane, configWindowController);
        editMenu.setGuiModificationHandler(this.guiModificationHandler);
        menus.add(editMenu);

        // ---- CODE GENERATION MENU ----
        codeGenerationMenu = new Menu(i18NRepo.getString("label.menu.generation"));
        MenuItem generateItem = new MenuItem(i18NRepo.getString("label.menu.generation.generate"));
        MenuItem generateCurrentFile = new MenuItem(i18NRepo.getString("label.menu.generation.file"));
        generateCurrentFile.setDisable(true);
        editorTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                if (newValue != null) {
                    String type = ((IEditorTab) newValue).getPresentedViewModelElement().getType();
                    if (type.equals(Types.BEHAVIOUR) ||
                            type.equals(Types.PLAN) ||
                            type.equals(Types.MASTERPLAN) ||
                            type.equals(Types.PLANTYPE) ||
                            type.equals(Types.TASKREPOSITORY)) {
                        generateCurrentFile.setDisable(false);
                    }
                } else {
                    generateCurrentFile.setDisable(true);
                }
            }
        });

        generateCurrentFile.setOnAction(e -> {
            long modelElementId = ((AbstractPlanTab) editorTabPane
                    .getSelectionModel().getSelectedItem()).getPresentedViewModelElement().getId();
            try {
                // TODO: couple codegeneration with gui (without dependencies)
//            	waitOnProgressWindow(() -> new Codegenerator().generate(modelElementId));
            } catch (RuntimeException ex) {
                LOG.error("error while generating code", ex);
                ErrorWindowController.createErrorWindow(i18NRepo.getString("label.error.codegen"), null);
            }
        });
        generateItem.setOnAction(e -> {
            try {
                // TODO: couple codegeneration with gui (without dependencies)
//            	waitOnProgressWindow(() -> new Codegenerator().generate());
            } catch (RuntimeException ex) {
                LOG.error("error while generating code", ex);
                ErrorWindowController.createErrorWindow(i18NRepo.getString("label.error.codegen"), null);
            }

        });

        codeGenerationMenu.getItems().addAll(generateCurrentFile, generateItem);
        menus.add(codeGenerationMenu);

        return menus;
    }

    public void setUpFileTreeView(String plansPath, String rolesPath, String tasksPath) {
        fileTreeView.getRoot().getChildren().clear();
        this.plansPath = plansPath;
        fileTreeView.setupPlansPath(this.plansPath);
        this.rolesPath = rolesPath;
        fileTreeView.setupRolesPath(this.rolesPath);
        this.tasksPath = tasksPath;
        fileTreeView.setupTaskPath(this.tasksPath);
    }

    public boolean isSelectedPlanElement(Node node) {
        Tab selectedItem = getEditorTabPane().getSelectionModel().getSelectedItem();
        if (selectedItem == null || ((AbstractPlanTab) selectedItem).getSelectedPlanElements() == null) {
            return false;
        }

        Pair<ViewModelElement, AbstractPlanElementContainer> o = ((AbstractPlanTab) selectedItem).getSelectedPlanElements().getValue().get(0);
        if (o != null && o.getValue() != null) {
            return o.getValue().equals(node) || o.getValue().getChildren().contains(node);
        } else {
            return false;
        }
    }

//--------------------------------------------------------------------------------------------
//  MENU STUFF
//--------------------------------------------------------------------------------------------

    public void enableMenuBar() {
        codeGenerationMenu.setDisable(false);
        fileMenu.setDisable(false);
    }
    public void setDeleteDisabled(boolean disabled) {
        editMenu.setDeleteItemDisabled(disabled);
    }

    public void disableRedo(boolean disable) {
        this.editMenu.setRedoItemDisabled(disable);
    }

    public void disableUndo(boolean disable) {
        this.editMenu.setUndoDisabled(disable);
    }

    /**
     * delegate to { EditorTabPane#openTab(java.nio.file.Path)}
     *
     * @param toOpen file that should be opened
     */
    public void openFile(ViewModelElement toOpen) {
        editorTabPane.openTab(toOpen);
    }

    private void waitOnProgressWindow(Runnable toWaitOn) {
        new Thread(() -> {
            toWaitOn.run();
            statusText.toFront();
            statusText.setOpacity(1.0);
            statusBlob.setOpacity(1.0);
            statusText.setLayoutY(statusBlob.getLayoutY() + statusText.getFont().getSize() + 2);
            statusText.setText(i18NRepo.getString("label.generation.completed"));
            statusText.setLayoutX(statusBlob.getLayoutX() + (statusBlob.getWidth() / 2) - statusText.getBoundsInLocal().getWidth() / 2);
            statusBlob.setVisible(true);
            statusText.setVisible(true);
            FadeTransition fadeTransition = new FadeTransition();
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.setDelay(Duration.seconds(2.0));
            fadeTransition.setNode(statusBlob);

            FadeTransition fadeTransition2 = new FadeTransition();
            fadeTransition2.setFromValue(1.0);
            fadeTransition2.setToValue(0.0);
            fadeTransition2.setDelay(Duration.seconds(2.0));
            fadeTransition2.setNode(statusText);

            fadeTransition.play();
            fadeTransition2.play();
            fadeTransition.onFinishedProperty().setValue(event -> {
                statusBlob.setVisible(false);
                statusText.setVisible(false);
            });
        }).start();
    }

    //	public void closeTabIfOpen (long modelElementId) {
    //        Optional<AbstractPlanTab> tabOptional = editorTabPane
    //                .getTabs()
    //                .stream()
    //                .map(e -> (AbstractPlanTab) e)
    //                .filter(e -> e.getEditable().equals(modelElementId))
    //                .findFirst();
    //        tabOptional.ifPresent(abstractEditorTab -> editorTabPane.getTabs().remove(abstractEditorTab));
    //    }

    //    public void closePropertyAndStatusTabIfOpen() {
    //        if(propertyAndStatusTabPane != null) {
    //            propertyAndStatusTabPane.getTabs().clear();
    //        }
    //    }
}
