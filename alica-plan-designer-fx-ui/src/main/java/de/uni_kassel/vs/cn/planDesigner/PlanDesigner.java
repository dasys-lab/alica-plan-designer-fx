package de.uni_kassel.vs.cn.planDesigner;
/**
 * Created by marci on 16.10.16.
 */

import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.alica.util.AllAlicaFiles;
import de.uni_kassel.vs.cn.planDesigner.ui.filebrowser.FileWatcherJob;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

public class PlanDesigner extends Application {

    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        EMFModelUtils.initializeEMF();
        launch(args);
        FileWatcherJob.stayAlive = false;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("test.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Carpe Noctem Plan Designer");
        Scene scene = new Scene(root);
        String cssPath = PlanDesigner.class.getResource("/styles.css").toExternalForm();
        scene.getStylesheets().add(cssPath);
        PlanDesigner.primaryStage = primaryStage;
        PlanDesigner.primaryStage.setScene(scene);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.show();
    }
}
