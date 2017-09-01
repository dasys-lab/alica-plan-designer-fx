package de.uni_kassel.vs.cn.planDesigner;
/**
 * Created by marci on 16.10.16.
 */

import de.uni_kassel.vs.cn.planDesigner.alica.configuration.WorkspaceManager;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.ui.filebrowser.FileWatcherJob;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URISyntaxException;

public class PlanDesigner extends Application {

    private static Stage primaryStage;

    public static Cursor FORBIDDEN_CURSOR = new ImageCursor(
            new Image(PlanDesigner.class.getClassLoader().getResourceAsStream("images/forbidden.png")));

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    private static boolean running;

    public static boolean isRunning() {
        return running;
    }

    public static void setRunning(boolean running) {
        PlanDesigner.running = running;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        new WorkspaceManager().init();
        EMFModelUtils.initializeEMF();
        launch(args);
        FileWatcherJob.stayAlive = false;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        running = true;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("test.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Carpe Noctem Plan Designer");
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                running = false;
            }
        });
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
