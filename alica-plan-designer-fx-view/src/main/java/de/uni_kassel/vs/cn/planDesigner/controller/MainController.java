package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.generator.Codegenerator;
import de.uni_kassel.vs.cn.planDesigner.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.AbstractPlanElementContainer;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.AbstractEditorTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.EditorTabPane;
import de.uni_kassel.vs.cn.planDesigner.view.filebrowser.PLDFileTreeView;
import de.uni_kassel.vs.cn.planDesigner.view.menu.EditMenu;
import de.uni_kassel.vs.cn.planDesigner.view.menu.NewResourceMenu;
import de.uni_kassel.vs.cn.planDesigner.view.properties.PropertyTabPane;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryTabPane;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    // SINGLETON
    private static volatile MainController instance;
    public static MainController getInstance() {
        if (instance == null)
        {
            synchronized(MainController.class)
            {
                if (instance == null)
                {
                    instance = new MainController();
                }
            }
        }

        return instance;
    }

    private static final Logger LOG = LogManager.getLogger(MainController.class);

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

    @FXML
    private Rectangle statusBlob;

    @FXML
    private Text statusText;

    private CommandStack commandStack = new CommandStack();

    private I18NRepo i18NRepo;

    private MainController ()
    {
        super();
        i18NRepo = I18NRepo.getInstance();
    }

    public void initialize(URL location, ResourceBundle resources) {
        fileTreeView.setController(this);
        editorTabPane.setCommandStack(commandStack);
        editorTabPane.getTabs().clear();
        repositoryTabPane.init();
        menuBar.getMenus().addAll(createMenus());
        propertyAndStatusTabPane.init(editorTabPane);
        statusText.setVisible(false);
    }

    public boolean isSelectedPlanElement(Node node) {
        Tab selectedItem = getEditorTabPane().getSelectionModel().getSelectedItem();
        if (selectedItem != null && ((AbstractEditorTab) selectedItem).getSelectedPlanElement() != null) {
            // TODO fix single
            Pair<PlanElement, AbstractPlanElementContainer> o = ((List<Pair<PlanElement, AbstractPlanElementContainer>>)
                    ((AbstractEditorTab) selectedItem).getSelectedPlanElement().getValue()).get(0);
            if (o != null && o.getValue() != null) {
                return o.getValue().equals(node) || o.getValue().getChildren().contains(node);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     *
     * @return
     */
    private List<Menu> createMenus() {
        List<Menu> menus = new ArrayList<>();
        Menu fileMenu = new Menu(i18NRepo.getString("label.menu.file"));
        fileMenu.getItems().add(new NewResourceMenu());
        MenuItem saveItem = new MenuItem(i18NRepo.getString("label.menu.file.save"));
        saveItem.setOnAction(event -> ((AbstractEditorTab<?>) editorTabPane.getSelectionModel().getSelectedItem()).save());
        saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        fileMenu.getItems().add(saveItem);
        menus.add(fileMenu);
        menus.add(new EditMenu(commandStack, editorTabPane));
        Menu codegenerationMenu = new Menu(i18NRepo.getString("label.menu.generation"));
        MenuItem regenerateItem = new MenuItem(i18NRepo.getString("label.menu.generation.regenerate"));
        MenuItem generateCurrentFile = new MenuItem(i18NRepo.getString("label.menu.generation.file"));
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
                ErrorWindowController.createErrorWindow(i18NRepo.getString("label.error.codegen"), null);
            }
        });
        regenerateItem.setOnAction(e -> {
            try {
            	waitOnProgressWindow(() -> new Codegenerator().generate());
                
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

	public void closeTabIfOpen (PlanElement planElement) {
        Optional<AbstractEditorTab<PlanElement>> tabOptional = editorTabPane
                .getTabs()
                .stream()
                .map(e -> (AbstractEditorTab<PlanElement>) e)
                .filter(e -> e.getEditable().equals(planElement))
                .findFirst();
        if (tabOptional.isPresent()) {
            editorTabPane.getTabs().remove(tabOptional.get());
        }
    }

    public void closePropertyAndStatusTabIfOpen() {
        if(propertyAndStatusTabPane != null) {
            propertyAndStatusTabPane.getTabs().clear();
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
