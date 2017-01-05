package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.ui.PLDFileTreeView;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.EditorTabPane;
import de.uni_kassel.vs.cn.planDesigner.ui.properties.PropertyTabPane;
import de.uni_kassel.vs.cn.planDesigner.ui.repo.RepositoryTabPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
    private ScrollPane leftScrollPane;

    @FXML
    private AnchorPane leftAnchor;

    @FXML
    private AnchorPane leftOuterAnchor;

    @FXML
    RepositoryTabPane repositoryTabPane;

    @FXML
    private EditorTabPane editorTabPane;

    public MainController() {
        super();
        MAIN_CONTROLLER = this;
    }

    public static MainController getInstance() {
        return MAIN_CONTROLLER;
    }

    public void initialize(URL location, ResourceBundle resources) {
        fileTreeView.setController(this);
        editorTabPane.getTabs().clear();
        repositoryTabPane.init();
        propertyAndStatusTabPane.init(editorTabPane);
    }

    /**
     * delegate to {@link EditorTabPane#openTab(java.nio.file.Path)}
     *
     * @param toOpen
     */
    public void openFile(File toOpen) {
        try {
            editorTabPane.openTab(toOpen.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
