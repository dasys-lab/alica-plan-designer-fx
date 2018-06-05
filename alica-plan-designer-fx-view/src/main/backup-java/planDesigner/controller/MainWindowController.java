package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.filebrowser.PLDFileTreeView;
import de.uni_kassel.vs.cn.planDesigner.view.menu.EditMenu;
import de.uni_kassel.vs.cn.planDesigner.view.menu.NewResourceMenu;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryTabPane;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    // SINGLETON
    private static volatile MainWindowController instance;
    public static MainWindowController getInstance() {
        if (instance == null)
        {
            synchronized(MainWindowController.class)
            {
                if (instance == null)
                {
                    instance = new MainWindowController();
                }
            }
        }

        return instance;
    }

    private static final Logger LOG = LogManager.getLogger(MainWindowController.class);
    public static Cursor FORBIDDEN_CURSOR = new ImageCursor(
            new Image(MainWindowController.class.getClassLoader().getResourceAsStream("images/forbidden.png")));

    @FXML
    private PLDFileTreeView pldFileTreeView;

    @FXML
    private PropertyTabPane propertyAndStatusTabPane;

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

    private I18NRepo i18NRepo;

    private MainWindowController()
    {
        super();
        i18NRepo = I18NRepo.getInstance();
    }

    public void initialize(URL location, ResourceBundle resources) {
        pldFileTreeView.setController(this);
        editorTabPane.getTabs().clear();
        menuBar.getMenus().addAll(createMenus());
        propertyAndStatusTabPane.init(editorTabPane);
        statusText.setVisible(false);
    }

    public boolean isSelectedPlanElement(Node node) {
        Tab selectedItem = getEditorTabPane().getSelectionModel().getSelectedItem();
        if (selectedItem == null || ((AbstractEditorTab) selectedItem).getSelectedPlanElements() == null) {
            return false;
        }

        Pair<Long, AbstractPlanElementContainer> o = ((AbstractEditorTab) selectedItem).getSelectedPlanElements().getValue().get(0);
        if (o != null && o.getValue() != null) {
            return o.getValue().equals(node) || o.getValue().getChildren().contains(node);
        } else {
            return false;
        }
    }

    private List<Menu> createMenus() {
        List<Menu> menus = new ArrayList<>();

        Menu fileMenu = new Menu(i18NRepo.getString("label.menu.file"));
        fileMenu.getItems().add(new NewResourceMenu());
        MenuItem saveItem = new MenuItem(i18NRepo.getString("label.menu.file.save"));
        saveItem.setOnAction(event -> ((AbstractEditorTab) editorTabPane.getSelectionModel().getSelectedItem()).save());
        saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        fileMenu.getItems().add(saveItem);
        menus.add(fileMenu);

        menus.add(new EditMenu(editorTabPane));

        Menu codegenerationMenu = new Menu(i18NRepo.getString("label.menu.generation"));
        MenuItem regenerateItem = new MenuItem(i18NRepo.getString("label.menu.generation.regenerate"));
        MenuItem generateCurrentFile = new MenuItem(i18NRepo.getString("label.menu.generation.file"));
        generateCurrentFile.setDisable(true);
        getEditorTabPane().getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // TODO: how to determine type of longId, for enabling code generation menu
//                if (((AbstractEditorTab) newValue).getEditable() instanceof AbstractPlan) {
//                    generateCurrentFile.setDisable(false);
//                }
            } else {
                generateCurrentFile.setDisable(true);
            }
        });

        generateCurrentFile.setOnAction(e -> {
            long modelElementId = ((AbstractEditorTab) getEditorTabPane()
                    .getSelectionModel().getSelectedItem()).getEditable();
            try {
                // TODO: couple codegeneration with gui (without dependencies)
//            	waitOnProgressWindow(() -> new Codegenerator().generate(modelElementId));
            } catch (RuntimeException ex) {
                LOG.error("error while generating code", ex);
                ErrorWindowController.createErrorWindow(i18NRepo.getString("label.error.codegen"), null);
            }
        });
        regenerateItem.setOnAction(e -> {
            try {
                // TODO: couple codegeneration with gui (without dependencies)
//            	waitOnProgressWindow(() -> new Codegenerator().generate());
            } catch (RuntimeException ex) {
                LOG.error("error while generating code", ex);
                ErrorWindowController.createErrorWindow(i18NRepo.getString("label.error.codegen"), null);
            }

        });
        codegenerationMenu.getItems().addAll(generateCurrentFile, regenerateItem);
        menus.add(codegenerationMenu);

        return menus;
    }

	private void waitOnProgressWindow(Runnable toWaitOn) {
		new Thread(() -> {
		    toWaitOn.run();
            statusText.toFront();
            statusText.setOpacity(1.0);
            statusBlob.setOpacity(1.0);
            statusText.setLayoutY(statusBlob.getLayoutY()+statusText.getFont().getSize()+2);
		    statusText.setText(i18NRepo.getString("label.generation.completed"));
            statusText.setLayoutX(statusBlob.getLayoutX() + (statusBlob.getWidth()/2)-statusText.getBoundsInLocal().getWidth()/2);
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

	public void closeTabIfOpen (long modelElementId) {
        Optional<AbstractEditorTab> tabOptional = editorTabPane
                .getTabs()
                .stream()
                .map(e -> (AbstractEditorTab) e)
                .filter(e -> e.getEditable().equals(modelElementId))
                .findFirst();
        tabOptional.ifPresent(abstractEditorTab -> editorTabPane.getTabs().remove(abstractEditorTab));
    }

    public void closePropertyAndStatusTabIfOpen() {
        if(propertyAndStatusTabPane != null) {
            propertyAndStatusTabPane.getTabs().clear();
        }
    }

    /**
     * delegate to {@link EditorTabPane#openTab(java.nio.file.Path)}
     *
     * @param toOpen file that should be opened
     */
    public void openFile(File toOpen) {
        editorTabPane.openTab(toOpen.toPath());
    }

    public EditorTabPane getEditorTabPane() {
        return editorTabPane;
    }

    public RepositoryTabPane getRepositoryTabPane() {
        return repositoryTabPane;
    }

    public PLDFileTreeView getPldFileTreeView() {
        return pldFileTreeView;
    }
}
