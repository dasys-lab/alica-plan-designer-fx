package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.ui.RepositoryTabPane;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.EditorTabPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import de.uni_kassel.vs.cn.planDesigner.ui.PLDFileTreeView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by marci on 16.10.16.
 */
public class MainController implements Initializable{
    @FXML
    private PLDFileTreeView fileTreeView;

    @FXML
    private TabPane propertyAndStatusTabPane;

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

    public void initialize(URL location, ResourceBundle resources) {
        fileTreeView.setController(this);
        editorTabPane.getTabs().clear();
        repositoryTabPane.init();
    }

    /**
     * delegate to {@link EditorTabPane#openTab(File)}
     * @param toOpen
     */
    public void openFile(File toOpen) {
        try {
            editorTabPane.openTab(toOpen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
