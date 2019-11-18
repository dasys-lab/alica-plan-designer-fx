package de.unikassel.vs.alica.planDesigner;

import de.unikassel.vs.alica.planDesigner.controller.IsDirtyWindowController;
import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.ConfigEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IConfigurationEventHandler;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;

public class PlanDesignerApplication extends Application {
    public static final String forbiddenCharacters = ".*[\\./\\*\\\\$§?\\[\\]!{}\\-äüö#\"%~'ÄÖÜß@,]+.*";

    private static Stage primaryStage;
    private static MainWindowController mainWindowController;

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

        Configurator.setRootLevel(Level.WARN);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("mainWindow.fxml"));
        // The next two lines replace this attribute in mainWindow.fxml::AnchorPane "fx:controller="MainWindowController"
        mainWindowController = MainWindowController.getInstance();
        fxmlLoader.setController(mainWindowController);
        fxmlLoader.setClassLoader(getClass().getClassLoader());
        Parent root = fxmlLoader.load();

        // TODO: does not load the key.xpm
        AlicaIcon icon = new AlicaIcon("icon", AlicaIcon.Size.NONE);
        primaryStage.getIcons().add(icon);
        // Mac dock icon workaround
        setOSXDockIcon(icon);
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

        IConfigurationEventHandler configEventHandler = mainWindowController.getConfigWindowController().getConfigEventHandler();
        HashMap<String, Double> params = configEventHandler.getPreferredWindowSettings();
        primaryStage.setHeight(params.get("height"));
        primaryStage.setWidth(params.get("width"));
        primaryStage.setX(params.get("x"));
        primaryStage.setY(params.get("y"));


        Scene scene = new Scene(root);
        String cssPath = PlanDesignerApplication.class.getResource("/styles.css").toExternalForm();
        scene.getStylesheets().add(cssPath);
        PlanDesignerApplication.primaryStage = primaryStage;
        PlanDesignerApplication.primaryStage.setScene(scene);

        KeyCombination keys = new KeyCodeCombination(KeyCode.S, KeyCombination.SHIFT_DOWN, KeyCombination.CONTROL_DOWN);
        scene.getAccelerators().put(keys, () -> {
            mainWindowController.getGuiModificationHandler().storeAll();
        });


        /**
         * Just for setting the divider default position to a certain position, after all resize events
         * of the main window during initialisation have happened. Setting it before, or by editing the fxml file
         * won't work, because the events reset the dividers position.
         */
        primaryStage.showingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                mainWindowController.getMainSplitPane().setDividerPosition(0, 0.16);
                observable.removeListener(this);
            }
        });

        primaryStage.show();
    }

    private void setOSXDockIcon(AlicaIcon icon) {
        String osString = System.getProperty("os.name");

        if (osString.contains("Mac") || osString.contains("osx")) {
            try {
                // does not work with Java versions > 9, for Java version 10 and higher use Taskbar.getTaskbar()
                Class appClass = Class.forName("com.apple.eawt.Application");
                Class params[] = new Class[]{Image.class};
                Method getApplication = appClass.getMethod("getApplication");
                Object application = getApplication.invoke(appClass);
                Method setDockIconImage = appClass.getMethod("setDockIconImage", params);
                URL iconURL = AlicaIcon.class.getClassLoader().getResource(icon.getResourcePath());
                Image image = new ImageIcon(iconURL).getImage();
                setDockIconImage.invoke(application, image);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            }
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        double height = getPrimaryStage().getHeight();
        double width = getPrimaryStage().getWidth();
        double x = getPrimaryStage().getX();
        double y = getPrimaryStage().getY();

        HashMap<String, Double> params = new HashMap<>();
        params.put("height", height);
        params.put("width", width);
        params.put("x", x);
        params.put("y", y);

        IConfigurationEventHandler configEventHandler = mainWindowController.getConfigWindowController().getConfigEventHandler();
        ConfigEvent configEvent = new ConfigEvent(ConfigEvent.WINDOW_SETTINGS);
        configEvent.setParameters(params);
        configEventHandler.handlePreferredWindowSettings(configEvent);
        System.exit(0);
    }
}
