package de.unikassel.vs.alica.planDesigner.controller;

import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiStatusHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.container.AbstractPlanElementContainer;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.AbstractPlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTabPane;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTab;
import de.unikassel.vs.alica.planDesigner.view.filebrowser.FileTreeView;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaCursor;
import de.unikassel.vs.alica.planDesigner.view.menu.EditMenu;
import de.unikassel.vs.alica.planDesigner.view.menu.NewResourceMenu;
import de.unikassel.vs.alica.planDesigner.view.model.SerializableViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import de.unikassel.vs.alica.planDesigner.view.repo.RepositoryTabPane;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
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
    public static Cursor FORBIDDEN_CURSOR = new AlicaCursor(AlicaCursor.Type.forbidden);

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

    @FXML
    private SplitPane mainSplitPane;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Text generatingText;

    @FXML
    private StackPane statusStackPane;

    @FXML
    private StackPane generatingStackPane;

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

    public SplitPane getMainSplitPane() {
        return mainSplitPane;
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

    public ConfigurationWindowController getConfigWindowController() {
        return configWindowController;
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
        fileTreeView.setController(this);

        if (configWindowController == null) {
            throw new RuntimeException("The member configWindowController need to be set through the public setter, before calling initialize()!");
        }

        // clear
        editorTabPane.getTabs().clear();

        repositoryTabPane.setGuiModificationHandler(guiModificationHandler);
        editorTabPane.setGuiModificationHandler(guiModificationHandler);

        // propertyAndStatusTabPane.init(editorTabPane);

        statusText.setVisible(false);
        menuBar.getMenus().addAll(createMenus());
        guiStatusHandler.handleGuiInitialized();
        setUpCodeGenerationProgressIndicator();

    }

    //--------------------------------------------------------------------------------------------
//  SETUP
//--------------------------------------------------------------------------------------------

    private void setUpCodeGenerationProgressIndicator() {
            generatingText.setText(i18NRepo.getString("label.menu.generation.generating"));
            progressBar.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() / 4);
            progressBar.setPrefHeight(menuBar.getHeight());
            generatingStackPane.setLayoutX(Screen.getPrimary().getVisualBounds().getWidth() / 2 - progressBar.getWidth() / 2);
            statusBlob.setWidth(progressBar.getWidth());
            statusBlob.setHeight(menuBar.getHeight());
            statusStackPane.setLayoutX(Screen.getPrimary().getVisualBounds().getWidth() / 2 - statusBlob.getWidth() / 2);
            statusText.setText(i18NRepo.getString("label.generation.completed"));
    }

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
            ((EditorTab) editorTabPane.getSelectionModel().getSelectedItem()).save();
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
                    String type = ((EditorTab) newValue).getSerializableViewModel().getType();
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
            ViewModelElement modelElement = ((AbstractPlanTab) editorTabPane
                    .getSelectionModel().getSelectedItem()).getSerializableViewModel();
            try {
                // TODO: couple codegeneration with gui (without dependencies)

                GuiModificationEvent event = new GuiModificationEvent(GuiEventType.GENERATE_ELEMENT, modelElement.getType(), modelElement.getName());
                event.setElementId(modelElement.getId());
                waitOnProgressLabel(() -> this.guiModificationHandler.generateCode(event));
            } catch (RuntimeException ex) {
                LOG.error("error while generating code", ex);
                ErrorWindowController.createErrorWindow(i18NRepo.getString("label.error.codegen"), null);
            }
        });
        generateItem.setOnAction(e -> {
            try {
                // TODO: couple codegeneration with gui (without dependencies)
                GuiModificationEvent event = new GuiModificationEvent(GuiEventType.GENERATE_ALL_ELEMENTS, "", "");
                waitOnProgressLabel(() -> this.guiModificationHandler.generateCode(event));
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
    public void openFile(SerializableViewModel toOpen) {
        editorTabPane.openTab(toOpen);
    }

    private void waitOnProgressLabel(Runnable toWaitOn) {
        new Thread(() -> {
            //Ping
            Platform.runLater(() -> {
                progressBar.setProgress(-1.0);
                generatingText.setText(i18NRepo.getString("label.menu.generation.generating"));
                progressBar.setPrefWidth(this.mainSplitPane.getParent().getScene().getWidth() / 4);
                progressBar.setPrefHeight(menuBar.getHeight());
                generatingStackPane.setLayoutX(mainSplitPane.getLayoutX() + this.mainSplitPane.getParent().getScene().getWidth() / 2 - progressBar.getWidth() / 2);
                progressBar.setVisible(true);
                generatingText.setVisible(true);
            });
            //Run generation
            toWaitOn.run();
            // Show Message
            Platform.runLater(() -> {
                progressBar.setProgress(0.0);
                progressBar.setVisible(false);
                generatingText.setVisible(false);
                statusBlob.setWidth(progressBar.getWidth());
                statusBlob.setHeight(menuBar.getHeight());
                statusStackPane.setLayoutX(mainSplitPane.getLayoutX() + this.mainSplitPane.getParent().getScene().getWidth() / 2 - statusBlob.getWidth() / 2);
                statusBlob.setVisible(true);
                statusText.toFront();
                statusText.setText(i18NRepo.getString("label.generation.completed"));
                statusText.setVisible(true);
                FadeTransition fadeTransitionStatusBlob = new FadeTransition();
                fadeTransitionStatusBlob.setFromValue(1.0);
                fadeTransitionStatusBlob.setToValue(0.0);
                fadeTransitionStatusBlob.setDelay(Duration.seconds(3.0));
                fadeTransitionStatusBlob.setNode(statusBlob);

                FadeTransition fadeTransitionStatusText = new FadeTransition();
                fadeTransitionStatusText.setFromValue(1.0);
                fadeTransitionStatusText.setToValue(0.0);
                fadeTransitionStatusText.setDelay(Duration.seconds(3.0));
                fadeTransitionStatusText.setNode(statusText);

                fadeTransitionStatusBlob.play();
                fadeTransitionStatusText.play();
                fadeTransitionStatusBlob.onFinishedProperty().setValue(event -> {
                    statusBlob.setVisible(false);
                    statusText.setVisible(false);
                });
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
