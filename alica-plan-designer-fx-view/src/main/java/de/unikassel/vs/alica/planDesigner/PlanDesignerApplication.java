package de.unikassel.vs.alica.planDesigner;

import de.unikassel.vs.alica.planDesigner.controller.IsDirtyWindowController;
import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;

public class PlanDesignerApplication extends Application {
    public static final String forbiddenCharacters = ".*[\\./\\*\\\\$§?\\[\\]!{}\\-äüö#\"%~'ÄÖÜß@,]+.*";

    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    private static boolean running;

    public static boolean isRunning() {
        return running;
    }

    public static void setRunning(boolean running) {
        PlanDesignerApplication.running = running;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        running = true;

        Logger.getRootLogger().setLevel(Level.WARN);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("mainWindow.fxml"));
        // The next two lines replace this attribute in mainWindow.fxml::AnchorPane "fx:controller="MainWindowController"
        MainWindowController mainWindowController = MainWindowController.getInstance();
        fxmlLoader.setController(mainWindowController);
        fxmlLoader.setClassLoader(getClass().getClassLoader());
        Parent root = fxmlLoader.load();

        // TODO: does not load the key.xpm
        primaryStage.getIcons().add(new AlicaIcon("icon", AlicaIcon.Size.NONE));
        primaryStage.setTitle("ALICA - Plan Designer");
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(IsDirtyWindowController.isAnyTabDirty()) {
                    IsDirtyWindowController.createMultipleTabsDirtyWindow(event);
                } else {
                    running = false;
                }
            }
        });

        Scene scene = new Scene(root);
        String cssPath = PlanDesignerApplication.class.getResource("/styles.css").toExternalForm();
        scene.getStylesheets().add(cssPath);
        PlanDesignerApplication.primaryStage = primaryStage;
        PlanDesignerApplication.primaryStage.setScene(scene);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.show();
    }
}
