package controller;

import configuration.Configuration;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import ui.PLDFileTreeView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by marci on 16.10.16.
 */
public class MainController implements Initializable{
    @FXML
    private TreeView<File> fileTreeView;

    @FXML
    private TabPane propertyAndStatusTabPane;

    public void initialize(URL location, ResourceBundle resources) {
        try {
            TreeItem treeItem = new FileTreeItem();
            treeItem.setExpanded(true);
            fileTreeView = new PLDFileTreeView(treeItem);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        propertyAndStatusTabPane = new TabPane();
    }

    private static class FileTreeItem extends TreeItem<File> {
        private ObservableList<TreeItem<File>> children;

        private Configuration configuration;

        public FileTreeItem() throws URISyntaxException {
            super(new File("/home/marci/cnws/src/cnc-msl/etc"));
            configuration = new Configuration();
            getChildren().addAll(new TreeItem<File>(configuration.getPlansPath().toFile()),
                    new TreeItem<File>(configuration.getRolesPath().toFile()),
                    new TreeItem<File>(configuration.getExpressionValidatorsPath().toFile()),
                    new TreeItem<File>(configuration.getMiscPath().toFile()));
        }
    }
}
